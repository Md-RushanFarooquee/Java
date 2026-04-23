"""Evaluation entrypoint for ASVspoof2019 LA models."""

from __future__ import annotations

import argparse
import csv
from datetime import datetime
import json
import pickle
from pathlib import Path
import sys

import numpy as np
import torch
import torch.nn.functional as F
from torch.utils.data import DataLoader

from src.config import DATA, FEATURES, SYSTEM
from src.dataset import ASVSpoofDataset
from src.train_pytorch import (
    build_model,
    pad_collate,
    resolve_dataset_root,
    resolve_protocol_file,
)
from src.utils.metrics import compute_accuracy_from_probabilities, compute_eer, compute_log_loss


@torch.no_grad()
def score_split(model, loader, device, use_amp, xgb_classifier=None):
    """Score a split and return positive-class probabilities + labels."""
    probabilities = []
    labels = []

    for features, lengths, batch_labels in loader:
        if xgb_classifier is not None:
            flattened = features.view(features.size(0), -1).cpu().numpy()
            probs = xgb_classifier.predict_proba(flattened)[:, 1]
            probabilities.extend(probs.tolist())
            labels.extend(batch_labels.numpy().tolist())
        else:
            features = features.to(device)
            lengths = lengths.to(device)
            batch_labels = batch_labels.to(device)

            with torch.amp.autocast(device_type=device.type, enabled=use_amp and device.type == "cuda"):
                logits = model(features, lengths)
                probs = F.softmax(logits, dim=1)[:, 1]

            probabilities.extend(probs.detach().cpu().numpy().tolist())
            labels.extend(batch_labels.detach().cpu().numpy().tolist())

    return np.asarray(probabilities, dtype=np.float64), np.asarray(labels, dtype=np.int64)


def build_loader(split: str, debug: bool = False, batch_size: int = 8):
    dataset_root = resolve_dataset_root()
    protocol_file = resolve_protocol_file(dataset_root, split)
    processed_dir = Path(DATA["processed_dir"]).resolve()
    dataset = ASVSpoofDataset(str(protocol_file), str(processed_dir), debug=debug)
    loader = DataLoader(
        dataset,
        batch_size=batch_size,
        shuffle=False,
        num_workers=0,
        pin_memory=False,
        collate_fn=pad_collate,
    )
    return dataset, loader


def load_attack_ids(split: str, debug: bool = False):
    """Load attack IDs in protocol order for alignment with scored samples."""
    dataset_root = resolve_dataset_root()
    protocol_file = resolve_protocol_file(dataset_root, split)

    with open(protocol_file, "r", encoding="utf-8") as handle:
        lines = handle.readlines()

    if debug:
        lines = lines[:100]

    attack_ids = []
    for line in lines:
        parts = line.strip().split()
        if len(parts) < 5:
            continue
        attack_ids.append(parts[3])

    return attack_ids


def compute_attack_breakdown(probabilities, labels, attack_ids):
    """Compute per-attack metrics against the bonafide pool."""
    if len(probabilities) != len(labels) or len(labels) != len(attack_ids):
        raise ValueError("Length mismatch among probabilities, labels, and attack IDs")

    probabilities = np.asarray(probabilities, dtype=np.float64)
    labels = np.asarray(labels, dtype=np.int64)

    bonafide_indices = [i for i, attack in enumerate(attack_ids) if attack == "-"]
    spoof_attacks = sorted({attack for attack in attack_ids if attack != "-"})

    rows = []
    for attack in spoof_attacks:
        attack_indices = [i for i, attack_id in enumerate(attack_ids) if attack_id == attack]
        subset_indices = bonafide_indices + attack_indices
        if len(subset_indices) < 2:
            continue

        subset_scores = probabilities[subset_indices]
        subset_labels = labels[subset_indices]

        eer, threshold = compute_eer(subset_scores, subset_labels)
        log_loss = compute_log_loss(subset_scores, subset_labels)
        accuracy = compute_accuracy_from_probabilities(subset_scores, subset_labels, threshold=threshold)

        predictions = (subset_scores >= threshold).astype(np.int64)
        tp = int(np.logical_and(predictions == 1, subset_labels == 1).sum())
        fp = int(np.logical_and(predictions == 1, subset_labels == 0).sum())
        fn = int(np.logical_and(predictions == 0, subset_labels == 1).sum())
        tn = int(np.logical_and(predictions == 0, subset_labels == 0).sum())

        tpr = tp / max(tp + fn, 1)
        tnr = tn / max(tn + fp, 1)
        fpr = fp / max(fp + tn, 1)
        fnr = fn / max(fn + tp, 1)

        rows.append(
            {
                "attack_id": attack,
                "num_bonafide": len(bonafide_indices),
                "num_spoof": len(attack_indices),
                "eer": float(eer),
                "threshold": float(threshold),
                "log_loss": float(log_loss),
                "accuracy": float(accuracy),
                "tp": tp,
                "fp": fp,
                "fn": fn,
                "tn": tn,
                "tpr": float(tpr),
                "tnr": float(tnr),
                "fpr": float(fpr),
                "fnr": float(fnr),
            }
        )

    return rows


def save_attack_breakdown(rows, output_prefix: Path):
    """Persist attack-wise metrics as CSV and JSON."""
    output_prefix.parent.mkdir(parents=True, exist_ok=True)
    csv_path = output_prefix.with_suffix(".csv")
    json_path = output_prefix.with_suffix(".json")

    with open(csv_path, "w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(
            handle,
            fieldnames=[
                "attack_id",
                "num_bonafide",
                "num_spoof",
                "eer",
                "threshold",
                "log_loss",
                "accuracy",
                "tp",
                "fp",
                "fn",
                "tn",
                "tpr",
                "tnr",
                "fpr",
                "fnr",
            ],
        )
        writer.writeheader()
        for row in rows:
            writer.writerow(row)

    with open(json_path, "w", encoding="utf-8") as handle:
        json.dump(rows, handle, indent=2)

    return csv_path, json_path


def save_overall_summary(output_path: Path, payload: dict):
    """Persist overall evaluation metrics summary."""
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with open(output_path, "w", encoding="utf-8") as handle:
        json.dump(payload, handle, indent=2)


def save_markdown_report(output_path: Path, summary_payload: dict, attack_rows: list[dict]):
    """Write a compact markdown evaluation report."""
    output_path.parent.mkdir(parents=True, exist_ok=True)

    lines = []
    lines.append("# ASVspoof2019 LA Evaluation Report")
    lines.append("")
    lines.append("## Overall")
    lines.append("")
    lines.append(f"- Split: {summary_payload['split']}")
    lines.append(f"- Model: {summary_payload['model']}")
    lines.append(f"- Feature: {summary_payload['feature']}")
    lines.append(f"- Samples: {summary_payload['num_samples']}")
    lines.append(f"- EER: {summary_payload['eer']:.6f}")
    lines.append(f"- Threshold: {summary_payload['threshold']:.6f}")
    if "threshold_source" in summary_payload:
        lines.append(f"- Threshold Source: {summary_payload['threshold_source']}")
    lines.append(f"- Log Loss: {summary_payload['log_loss']:.6f}")
    lines.append(f"- Accuracy: {summary_payload['accuracy']:.6f}")
    lines.append("")

    lines.append("## Run Metadata")
    lines.append("")
    lines.append(f"- Evaluated At (UTC): {summary_payload.get('evaluated_at_utc', 'n/a')}")
    lines.append(f"- Checkpoint: {summary_payload.get('checkpoint_path', 'n/a')}")
    lines.append(f"- Command: {summary_payload.get('command', 'n/a')}")
    lines.append("")

    if attack_rows:
        lines.append("## Attack-Wise Breakdown")
        lines.append("")
        lines.append("| Attack | #Bonafide | #Spoof | EER | Accuracy | TP | FP | FN | TN | FPR | FNR |")
        lines.append("|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|")
        for row in attack_rows:
            lines.append(
                "| "
                f"{row['attack_id']} | {row['num_bonafide']} | {row['num_spoof']} | "
                f"{row['eer']:.4f} | {row['accuracy']:.4f} | "
                f"{row['tp']} | {row['fp']} | {row['fn']} | {row['tn']} | "
                f"{row['fpr']:.4f} | {row['fnr']:.4f} |"
            )
        lines.append("")

        # Highlight hardest/easiest attacks for quick report writing.
        top_k = min(5, len(attack_rows))
        hardest_by_eer = sorted(attack_rows, key=lambda r: r["eer"], reverse=True)[:top_k]
        easiest_by_eer = sorted(attack_rows, key=lambda r: r["eer"])[:top_k]
        hardest_by_fnr = sorted(attack_rows, key=lambda r: r["fnr"], reverse=True)[:top_k]

        lines.append("## Hardest Attacks")
        lines.append("")
        lines.append(f"Top {top_k} by EER:")
        for row in hardest_by_eer:
            lines.append(
                f"- {row['attack_id']}: EER={row['eer']:.4f}, "
                f"FNR={row['fnr']:.4f}, FPR={row['fpr']:.4f}, Accuracy={row['accuracy']:.4f}"
            )
        lines.append("")

        lines.append(f"Top {top_k} by FNR:")
        for row in hardest_by_fnr:
            lines.append(
                f"- {row['attack_id']}: FNR={row['fnr']:.4f}, "
                f"EER={row['eer']:.4f}, FPR={row['fpr']:.4f}, Accuracy={row['accuracy']:.4f}"
            )
        lines.append("")

        lines.append("## Easiest Attacks")
        lines.append("")
        lines.append(f"Top {top_k} by lowest EER:")
        for row in easiest_by_eer:
            lines.append(
                f"- {row['attack_id']}: EER={row['eer']:.4f}, "
                f"FNR={row['fnr']:.4f}, FPR={row['fpr']:.4f}, Accuracy={row['accuracy']:.4f}"
            )
        lines.append("")

    with open(output_path, "w", encoding="utf-8") as handle:
        handle.write("\n".join(lines))


def main():
    parser = argparse.ArgumentParser(description="Evaluate an ASVspoof2019 LA checkpoint.")
    parser.add_argument("--checkpoint", type=str, required=True, help="Path to .pth checkpoint")
    parser.add_argument("--split", type=str, default="dev", choices=["dev", "eval"], help="Split to score")
    parser.add_argument("--batch_size", type=int, default=8)
    parser.add_argument("--debug", action="store_true")
    parser.add_argument(
        "--save_reports",
        action="store_true",
        help="Save summary JSON, attack-wise CSV/JSON, and markdown report artifacts.",
    )
    parser.add_argument(
        "--threshold_file",
        type=str,
        default=None,
        help="Optional JSON file containing a frozen threshold saved by calibration.",
    )
    args = parser.parse_args()

    checkpoint_path = Path(args.checkpoint)
    if not checkpoint_path.exists():
        raise FileNotFoundError(f"Checkpoint not found: {checkpoint_path}")

    device = torch.device(SYSTEM["device"] if torch.cuda.is_available() else "cpu")
    use_amp = bool(SYSTEM["use_amp"] and device.type == "cuda")

    if checkpoint_path.suffix.lower() == ".pkl":
        with open(checkpoint_path, "rb") as handle:
            checkpoint = pickle.load(handle)
    else:
        checkpoint = torch.load(checkpoint_path, map_location=device)

    model_name = checkpoint["model"]
    feature_name = checkpoint.get("feature", FEATURES["type"])
    input_dim = int(checkpoint.get("input_dim", 0))
    xgb_classifier = checkpoint.get("classifier") if model_name == "xgboost" else None

    FEATURES["type"] = feature_name
    _, loader = build_loader(args.split, debug=args.debug, batch_size=args.batch_size)

    model = None
    if model_name != "xgboost":
        model = build_model(model_name, input_dim=input_dim).to(device)
        state_dict = checkpoint.get("model_state_dict", checkpoint.get("state_dict"))
        if state_dict is None:
            raise KeyError("Checkpoint missing model_state_dict/state_dict")
        model.load_state_dict(state_dict)

    probabilities, labels = score_split(model, loader, device, use_amp, xgb_classifier=xgb_classifier)
    eer, threshold = compute_eer(probabilities, labels)

    threshold_source = "checkpoint"
    if args.threshold_file is not None:
        threshold_path = Path(args.threshold_file)
        if not threshold_path.exists():
            raise FileNotFoundError(f"Threshold file not found: {threshold_path}")
        with open(threshold_path, "r", encoding="utf-8") as handle:
            threshold_payload = json.load(handle)
        threshold = float(threshold_payload["threshold"])
        threshold_source = f"file:{threshold_path}"
    elif "threshold" in checkpoint:
        threshold = float(checkpoint["threshold"])
        threshold_source = "checkpoint"
    else:
        raise KeyError(
            "No saved threshold found. Provide --threshold_file or a checkpoint with embedded threshold."
        )

    log_loss = compute_log_loss(probabilities, labels)
    accuracy = compute_accuracy_from_probabilities(probabilities, labels, threshold=threshold)

    attack_rows = []
    try:
        attack_ids = load_attack_ids(args.split, debug=args.debug)
        attack_rows = compute_attack_breakdown(probabilities, labels, attack_ids)
    except Exception as exc:
        print(f"Warning: could not compute attack-wise breakdown: {exc}")

    if args.save_reports and attack_rows:
        output_prefix = Path("data/results") / f"{model_name}_{feature_name}_{args.split}_attack_breakdown"
        csv_path, json_path = save_attack_breakdown(attack_rows, output_prefix)
        print(f"Attack-wise report CSV: {csv_path}")
        print(f"Attack-wise report JSON: {json_path}")

    if args.save_reports:
        run_command = " ".join(sys.argv)
        summary_path = Path("data/results") / f"{model_name}_{feature_name}_{args.split}_summary.json"
        summary_payload = {
            "split": args.split,
            "model": model_name,
            "feature": feature_name,
            "evaluated_at_utc": datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%SZ"),
            "checkpoint_path": str(checkpoint_path.resolve()),
            "command": run_command,
            "num_samples": int(len(labels)),
            "eer": float(eer),
            "threshold": float(threshold),
            "threshold_source": threshold_source,
            "log_loss": float(log_loss),
            "accuracy": float(accuracy),
            "attack_rows": int(len(attack_rows)),
        }
        save_overall_summary(summary_path, summary_payload)
        print(f"Overall summary JSON:  {summary_path}")

        report_path = Path("experiments") / f"{model_name}_{feature_name}_{args.split}_report.md"
        save_markdown_report(report_path, summary_payload, attack_rows)
        print(f"Markdown report:       {report_path}")

    print("=" * 80)
    print(f"Evaluation Split: {args.split.upper()}")
    print(f"Model:            {model_name}")
    print(f"Feature:          {feature_name}")
    print(f"Samples:          {len(labels)}")
    print(f"EER:              {eer:.6f}")
    print(f"Threshold:        {threshold:.6f}")
    print(f"Log Loss:         {log_loss:.6f}")
    print(f"Accuracy:         {accuracy:.6f}")
    if attack_rows:
        print(f"Attack rows:      {len(attack_rows)}")
    print("=" * 80)


if __name__ == "__main__":
    main()
