import argparse
import csv
import json
import time
from pathlib import Path

import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
from torch.utils.data import DataLoader

from src.config import DATA, FEATURES, TRAINING, SYSTEM
from src.dataset import ASVSpoofDataset
from src.utils.core_utils import seed_everything
from src.utils.metrics import compute_accuracy_from_probabilities, compute_eer, compute_log_loss


def resolve_dataset_root() -> Path:
    """Locate the bundled ASVspoof2019_LA dataset folder."""
    candidates = [Path("ASVspoof2019_LA"), Path("data") / "raw"]
    for candidate in candidates:
        if candidate.exists() and (candidate / "ASVspoof2019_LA_cm_protocols").exists():
            return candidate.resolve()

    fallback = Path("ASVspoof2019_LA")
    if fallback.exists():
        return fallback.resolve()

    raise FileNotFoundError("Could not locate ASVspoof2019_LA dataset folder.")


def resolve_protocol_file(dataset_root: Path, split: str) -> Path:
    protocol_dir = dataset_root / "ASVspoof2019_LA_cm_protocols"
    mapping = {
        "train": protocol_dir / "ASVspoof2019.LA.cm.train.trn.txt",
        "dev": protocol_dir / "ASVspoof2019.LA.cm.dev.trl.txt",
        "eval": protocol_dir / "ASVspoof2019.LA.cm.eval.trl.txt",
    }
    return mapping[split]


def infer_feature_shape(npy_dir: Path) -> tuple[int, int]:
    """Inspect the first cached feature file to infer tensor dimensions."""
    for npy_path in sorted(npy_dir.glob("*.npy")):
        feature = np.load(npy_path)
        if feature.ndim == 2:
            return feature.shape
        if feature.ndim == 1:
            return feature.shape[0], 1
    raise FileNotFoundError(f"No .npy features found in {npy_dir}")


def pad_collate(batch):
    """Pad variable-length feature sequences and return lengths for masking."""
    features, labels = zip(*batch)
    lengths = torch.tensor([item.shape[0] for item in features], dtype=torch.long)
    feature_dim = features[0].shape[1] if features[0].ndim == 2 else 1
    max_length = int(lengths.max().item())

    padded = torch.zeros(len(features), max_length, feature_dim, dtype=torch.float32)
    for index, feature in enumerate(features):
        if feature.ndim == 1:
            feature = feature.unsqueeze(-1)
        padded[index, : feature.shape[0], : feature.shape[1]] = feature

    labels = torch.tensor(labels, dtype=torch.long)
    return padded, lengths, labels


class TinyCNN(nn.Module):
    def __init__(self, input_dim: int, num_classes: int = 2):
        super().__init__()
        self.net = nn.Sequential(
            nn.Conv1d(input_dim, 32, kernel_size=5, padding=2),
            nn.ReLU(),
            nn.Conv1d(32, 64, kernel_size=3, padding=1),
            nn.ReLU(),
            nn.AdaptiveAvgPool1d(1),
        )
        self.classifier = nn.Linear(64, num_classes)

    def forward(self, x, lengths=None):
        x = x.transpose(1, 2)
        x = self.net(x).squeeze(-1)
        return self.classifier(x)


class TinyLSTM(nn.Module):
    def __init__(self, input_dim: int, hidden_size: int = 64, num_classes: int = 2):
        super().__init__()
        self.lstm = nn.LSTM(
            input_size=input_dim,
            hidden_size=hidden_size,
            batch_first=True,
            bidirectional=True,
        )
        self.classifier = nn.Linear(hidden_size * 2, num_classes)

    def forward(self, x, lengths=None):
        if lengths is not None:
            packed = nn.utils.rnn.pack_padded_sequence(
                x, lengths.cpu(), batch_first=True, enforce_sorted=False
            )
            _, (hidden, _) = self.lstm(packed)
        else:
            _, (hidden, _) = self.lstm(x)

        hidden = torch.cat([hidden[-2], hidden[-1]], dim=1)
        return self.classifier(hidden)


class TinyCRNN(nn.Module):
    def __init__(self, input_dim: int, hidden_size: int = 64, num_classes: int = 2):
        super().__init__()
        self.frontend = nn.Sequential(
            nn.Conv1d(input_dim, 32, kernel_size=5, padding=2),
            nn.ReLU(),
            nn.Conv1d(32, 32, kernel_size=3, padding=1),
            nn.ReLU(),
        )
        self.lstm = nn.LSTM(32, hidden_size, batch_first=True, bidirectional=True)
        self.classifier = nn.Linear(hidden_size * 2, num_classes)

    def forward(self, x, lengths=None):
        x = x.transpose(1, 2)
        x = self.frontend(x).transpose(1, 2)
        if lengths is not None:
            packed = nn.utils.rnn.pack_padded_sequence(
                x, lengths.cpu(), batch_first=True, enforce_sorted=False
            )
            _, (hidden, _) = self.lstm(packed)
        else:
            _, (hidden, _) = self.lstm(x)
        hidden = torch.cat([hidden[-2], hidden[-1]], dim=1)
        return self.classifier(hidden)


def build_model(model_name: str, input_dim: int) -> nn.Module:
    if model_name == "cnn":
        return TinyCNN(input_dim)
    if model_name == "lstm":
        return TinyLSTM(input_dim, hidden_size=64)
    if model_name == "crnn":
        return TinyCRNN(input_dim, hidden_size=64)
    raise ValueError(f"Unsupported model: {model_name}")


def compute_accuracy(logits: torch.Tensor, labels: torch.Tensor) -> float:
    predictions = torch.argmax(logits, dim=1)
    return (predictions == labels).float().mean().item()


def train_one_epoch(model, loader, optimizer, scaler, device, use_amp, accumulation_steps):
    model.train()
    criterion = nn.CrossEntropyLoss()
    running_loss = 0.0
    running_accuracy = 0.0
    optimizer.zero_grad(set_to_none=True)

    for step, (features, lengths, labels) in enumerate(loader, start=1):
        features = features.to(device)
        lengths = lengths.to(device)
        labels = labels.to(device)

        with torch.amp.autocast(device_type=device.type, enabled=use_amp and device.type == "cuda"):
            logits = model(features, lengths)
            loss = criterion(logits, labels) / accumulation_steps

        scaler.scale(loss).backward()

        if step % accumulation_steps == 0 or step == len(loader):
            scaler.step(optimizer)
            scaler.update()
            optimizer.zero_grad(set_to_none=True)

        running_loss += loss.item() * accumulation_steps
        running_accuracy += compute_accuracy(logits.detach(), labels)

    return running_loss / max(len(loader), 1), running_accuracy / max(len(loader), 1)


@torch.no_grad()
def evaluate(model, loader, device, use_amp):
    model.eval()
    criterion = nn.CrossEntropyLoss()
    total_loss = 0.0
    probabilities = []
    labels_all = []

    for features, lengths, labels in loader:
        features = features.to(device)
        lengths = lengths.to(device)
        labels = labels.to(device)

        with torch.amp.autocast(device_type=device.type, enabled=use_amp and device.type == "cuda"):
            logits = model(features, lengths)
            loss = criterion(logits, labels)

        total_loss += loss.item()
        probs = F.softmax(logits, dim=1)[:, 1]
        probabilities.extend(probs.detach().cpu().numpy().tolist())
        labels_all.extend(labels.detach().cpu().numpy().tolist())

    probabilities_np = np.asarray(probabilities, dtype=np.float64)
    labels_np = np.asarray(labels_all, dtype=np.int64)
    eer, threshold = compute_eer(probabilities_np, labels_np)
    log_loss = compute_log_loss(probabilities_np, labels_np)
    accuracy = compute_accuracy_from_probabilities(probabilities_np, labels_np, threshold=threshold)

    return {
        "loss": total_loss / max(len(loader), 1),
        "accuracy": accuracy,
        "eer": eer,
        "threshold": threshold,
        "log_loss": log_loss,
    }


@torch.no_grad()
def evaluate_dev(model, dataloader, device=None, use_amp=False):
    """Evaluate on dev data and return (eer, threshold)."""
    if device is None:
        device = next(model.parameters()).device

    model.eval()
    scores = []
    labels = []

    for features, lengths, batch_labels in dataloader:
        features = features.to(device)
        lengths = lengths.to(device)

        with torch.amp.autocast(device_type=device.type, enabled=use_amp and device.type == "cuda"):
            logits = model(features, lengths)
            probabilities = F.softmax(logits, dim=1)[:, 1]

        scores.extend(probabilities.detach().cpu().numpy().tolist())
        labels.extend(batch_labels.detach().cpu().numpy().tolist())

    eer, threshold = compute_eer(np.asarray(scores), np.asarray(labels))
    return eer, threshold


def write_metrics_header(csv_path: Path):
    csv_path.parent.mkdir(parents=True, exist_ok=True)
    if not csv_path.exists():
        with open(csv_path, "w", newline="", encoding="utf-8") as handle:
            writer = csv.writer(handle)
            writer.writerow(["model", "feature", "epoch", "loss", "accuracy", "EER", "lr", "batch_size", "time"])


def append_metrics(csv_path: Path, row):
    with open(csv_path, "a", newline="", encoding="utf-8") as handle:
        writer = csv.writer(handle)
        writer.writerow(row)


def main(args):
    seed_everything(42)

    FEATURES["type"] = args.feature

    device = torch.device(SYSTEM["device"] if torch.cuda.is_available() else "cpu")
    use_amp = bool(SYSTEM["use_amp"] and device.type == "cuda")

    if device.type == "cuda":
        print(f"--- Training {args.model.upper()} | GPU: {torch.cuda.get_device_name(0)} ---")
    else:
        print(f"--- Training {args.model.upper()} | CPU ---")

    print(f"Feature Set: {FEATURES['type'].upper()} | AMP: {use_amp}")

    default_bs = {
        "cnn": TRAINING["cnn_batch_size"],
        "lstm": TRAINING["lstm_batch_size"],
        "crnn": 4,
    }
    batch_size = args.batch_size or default_bs[args.model]
    print(f"Batch Size: {batch_size} | Epochs: {args.epochs}")

    dataset_root = resolve_dataset_root()
    train_protocol_file = resolve_protocol_file(dataset_root, "train")
    dev_protocol_file = resolve_protocol_file(dataset_root, "dev")
    processed_dir = Path(DATA["processed_dir"]).resolve()
    feature_dir = processed_dir / FEATURES["type"]

    if args.debug:
        print("⚠️ DEBUG MODE: Using only 100 samples")

    train_dataset = ASVSpoofDataset(str(train_protocol_file), str(processed_dir), debug=args.debug)
    val_dataset = ASVSpoofDataset(str(dev_protocol_file), str(processed_dir), debug=args.debug)
    if len(train_dataset) == 0:
        raise RuntimeError("Train dataset is empty. Run feature extraction first.")
    if len(val_dataset) == 0:
        raise RuntimeError("Dev dataset is empty. Run feature extraction first.")

    train_loader = DataLoader(
        train_dataset,
        batch_size=batch_size,
        shuffle=True,
        num_workers=0,
        pin_memory=False,
        collate_fn=pad_collate,
    )
    val_loader = DataLoader(
        val_dataset,
        batch_size=batch_size,
        shuffle=False,
        num_workers=0,
        pin_memory=False,
        collate_fn=pad_collate,
    )

    sample_feature, _ = train_dataset[0]
    if sample_feature.ndim == 1:
        input_dim = 1
    else:
        input_dim = sample_feature.shape[1]

    model = build_model(args.model, input_dim=input_dim).to(device)
    optimizer = torch.optim.Adam(model.parameters(), lr=TRAINING["learning_rate"])
    scheduler = torch.optim.lr_scheduler.ReduceLROnPlateau(
        optimizer,
        mode="min",
        factor=0.5,
        patience=2,
        min_lr=1e-6,
    )
    scaler = torch.amp.GradScaler("cuda", enabled=use_amp)

    csv_path = Path("data/results") / f"{args.model}_{FEATURES['type']}_metrics.csv"
    write_metrics_header(csv_path)

    best_val_loss = float("inf")
    best_threshold = 0.5
    best_epoch = 0
    no_improvement_epochs = 0
    best_path = Path("checkpoints") / f"{args.model}_{FEATURES['type']}.pth"
    threshold_path = Path("data/results") / f"{args.model}_{FEATURES['type']}_threshold.json"
    best_path.parent.mkdir(parents=True, exist_ok=True)
    threshold_path.parent.mkdir(parents=True, exist_ok=True)

    print(f"Training samples: {len(train_dataset)} | Validation samples: {len(val_dataset)}")
    print(f"Feature cache: {feature_dir}")
    print(f"Checkpoint: {best_path}")
    print(f"Early stopping patience: {args.early_stopping_patience}")

    for epoch in range(1, args.epochs + 1):
        start_time = time.time()
        train_loss, train_acc = train_one_epoch(
            model,
            train_loader,
            optimizer,
            scaler,
            device,
            use_amp,
            TRAINING["accumulation_steps"],
        )
        val_metrics = evaluate(model, val_loader, device, use_amp)
        val_loss = val_metrics["loss"]
        val_acc = val_metrics["accuracy"]
        val_eer, val_threshold = evaluate_dev(model, val_loader, device=device, use_amp=use_amp)
        val_log_loss = val_metrics["log_loss"]
        scheduler.step(val_loss)
        elapsed = time.time() - start_time

        lr = optimizer.param_groups[0]["lr"]
        append_metrics(
            csv_path,
            [
                args.model,
                FEATURES["type"],
                epoch,
                val_loss,
                val_acc,
                val_eer,
                lr,
                batch_size,
                round(elapsed, 4),
            ],
        )

        print(
            f"Epoch {epoch:03d} | train_loss={train_loss:.4f} | train_acc={train_acc:.4f} | "
            f"val_loss={val_loss:.4f} | val_acc={val_acc:.4f} | val_eer={val_eer:.4f} | "
            f"val_logloss={val_log_loss:.4f} | lr={lr:.6f} | time={elapsed:.2f}s"
        )

        if val_loss < best_val_loss:
            best_val_loss = val_loss
            best_threshold = val_threshold
            best_epoch = epoch
            no_improvement_epochs = 0
            torch.save(
                {
                    "model": args.model,
                    "feature": FEATURES["type"],
                    "epoch": epoch,
                    "model_state_dict": model.state_dict(),
                    "state_dict": model.state_dict(),
                    "input_dim": input_dim,
                    "val_loss": val_loss,
                    "eer": val_eer,
                    "val_eer": val_eer,
                    "threshold": val_threshold,
                },
                best_path,
            )
            print(f"  ✓ Saved best checkpoint -> {best_path}")

            threshold_payload = {
                "model": args.model,
                "feature": FEATURES["type"],
                "split": "dev",
                "epoch": epoch,
                "threshold": float(val_threshold),
                "eer": float(val_eer),
                "log_loss": float(val_log_loss),
                "val_loss": float(val_loss),
                "val_accuracy": float(val_acc),
            }
            with open(threshold_path, "w", encoding="utf-8") as handle:
                json.dump(threshold_payload, handle, indent=2)
            print(f"  ✓ Saved threshold artifact -> {threshold_path}")
        else:
            no_improvement_epochs += 1

        if no_improvement_epochs >= args.early_stopping_patience:
            print(
                f"Early stopping triggered after {no_improvement_epochs} non-improving epochs. "
                f"Best epoch={best_epoch}, best_val_loss={best_val_loss:.4f}"
            )
            break

    print(
        f"Training complete. Best epoch={best_epoch}, best_val_loss={best_val_loss:.4f}, "
        f"best_threshold={best_threshold:.6f}"
    )


if __name__ == "__main__":
    parser = argparse.ArgumentParser()

    parser.add_argument(
        "--model",
        type=str,
        required=True,
        choices=["cnn", "lstm", "crnn"],
    )
    parser.add_argument(
        "--feature",
        type=str,
        default=FEATURES["type"],
        choices=["mfcc", "lfcc"],
        help="Feature type to train on",
    )
    parser.add_argument("--batch_size", type=int, default=None)
    parser.add_argument("--epochs", type=int, default=TRAINING["epochs"])
    parser.add_argument("--debug", action="store_true")
    parser.add_argument(
        "--early_stopping_patience",
        type=int,
        default=10,
        help="Number of epochs with no val-loss improvement before stopping",
    )

    args = parser.parse_args()
    main(args)
