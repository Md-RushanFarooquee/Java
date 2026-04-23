"""Classical baseline training for ASVspoof2019 LA (XGBoost first)."""

from __future__ import annotations

import argparse
import csv
import pickle
import time
from pathlib import Path

import numpy as np
import torch
from sklearn.svm import SVC

try:
    import xgboost as xgb
except ImportError as exc:  # pragma: no cover - surfaced at runtime if dependency missing
    xgb = None
    _xgb_import_error = exc

from src.config import DATA, FEATURES, TRAINING, SYSTEM
from src.dataset import ASVSpoofDataset
from src.train_pytorch import resolve_dataset_root, resolve_protocol_file
from src.utils.core_utils import seed_everything
from src.utils.metrics import compute_accuracy_from_probabilities, compute_eer, compute_log_loss


def _load_flat_features(protocol_split: str, debug: bool = False):
    dataset_root = resolve_dataset_root()
    protocol_file = resolve_protocol_file(dataset_root, protocol_split)
    processed_dir = Path(DATA["processed_dir"]).resolve()
    dataset = ASVSpoofDataset(str(protocol_file), str(processed_dir), debug=debug)

    features = []
    labels = []
    for feature, label in dataset:
        feature_np = feature.detach().cpu().numpy()
        features.append(feature_np.reshape(-1))
        labels.append(int(label.item()))

    return np.asarray(features, dtype=np.float32), np.asarray(labels, dtype=np.int64)


def _write_metrics(csv_path: Path, row):
    csv_path.parent.mkdir(parents=True, exist_ok=True)
    if not csv_path.exists():
        with open(csv_path, "w", newline="", encoding="utf-8") as handle:
            writer = csv.writer(handle)
            writer.writerow(["model", "feature", "epoch", "loss", "accuracy", "EER", "lr", "batch_size", "time"])

    with open(csv_path, "a", newline="", encoding="utf-8") as handle:
        writer = csv.writer(handle)
        writer.writerow(row)


def main():
    parser = argparse.ArgumentParser(description="Train classical baselines on flattened features.")
    parser.add_argument("--model", type=str, default="xgboost", choices=["xgboost", "svm"])
    parser.add_argument("--feature", type=str, default=FEATURES["type"], choices=["mfcc", "lfcc"])
    parser.add_argument("--debug", action="store_true")
    parser.add_argument("--output_dir", type=str, default="data/results")
    parser.add_argument("--checkpoint_dir", type=str, default="checkpoints")
    args = parser.parse_args()

    if args.model == "xgboost" and xgb is None:
        raise ImportError(
            "xgboost is not installed. Install it to run the baseline."
        ) from _xgb_import_error

    seed_everything(42)
    FEATURES["type"] = args.feature

    device = torch.device(SYSTEM["device"] if torch.cuda.is_available() else "cpu")
    print(f"--- Training {args.model.upper()} Baseline | Device: {device.type.upper()} ---")
    print(f"Feature Set: {FEATURES['type'].upper()}")

    start_time = time.time()
    train_x, train_y = _load_flat_features("train", debug=args.debug)
    dev_x, dev_y = _load_flat_features("dev", debug=args.debug)

    print(f"Train shape: {train_x.shape} | Dev shape: {dev_x.shape}")

    if args.model == "xgboost":
        clf = xgb.XGBClassifier(
            n_estimators=FEATURES.get("xgb_n_estimators", 100),
            max_depth=6,
            learning_rate=0.1,
            subsample=0.8,
            colsample_bytree=0.8,
            tree_method="hist",
            objective="binary:logistic",
            eval_metric="logloss",
            random_state=42,
            n_jobs=4,
        )
        clf.fit(
            train_x,
            train_y,
            eval_set=[(dev_x, dev_y)],
            verbose=False,
        )
        logged_lr = 0.1
    else:
        clf = SVC(
            kernel="rbf",
            C=1.0,
            gamma="scale",
            probability=True,
            random_state=42,
        )
        clf.fit(train_x, train_y)
        logged_lr = 0.0

    dev_probabilities = clf.predict_proba(dev_x)[:, 1]
    dev_loss = compute_log_loss(dev_probabilities, dev_y)
    dev_eer, threshold = compute_eer(dev_probabilities, dev_y)
    dev_accuracy = compute_accuracy_from_probabilities(dev_probabilities, dev_y, threshold=threshold)
    elapsed = time.time() - start_time

    output_dir = Path(args.output_dir)
    checkpoint_dir = Path(args.checkpoint_dir)
    output_dir.mkdir(parents=True, exist_ok=True)
    checkpoint_dir.mkdir(parents=True, exist_ok=True)

    model_name = f"{args.model}_{args.feature}"
    csv_path = output_dir / f"{model_name}_metrics.csv"
    _write_metrics(
        csv_path,
        [model_name, args.feature, 1, dev_loss, dev_accuracy, dev_eer, logged_lr, 0, round(elapsed, 4)],
    )

    checkpoint_path = checkpoint_dir / f"{model_name}.pkl"
    with open(checkpoint_path, "wb") as handle:
        pickle.dump(
            {
                "model": args.model,
                "feature": args.feature,
                "threshold": float(threshold),
                "classifier": clf,
            },
            handle,
        )

    print("=" * 80)
    print(f"Saved checkpoint: {checkpoint_path}")
    print(f"Dev Loss:  {dev_loss:.6f}")
    print(f"Dev EER:   {dev_eer:.6f}")
    print(f"Threshold: {threshold:.6f}")
    print(f"Accuracy:  {dev_accuracy:.6f}")
    print(f"CSV:       {csv_path}")
    print("=" * 80)


if __name__ == "__main__":
    main()
