"""Calibrate a frozen decision threshold on the dev split only."""

from __future__ import annotations

import argparse
import json
import pickle
from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

import torch
from torch.utils.data import DataLoader

from src.config import DATA, FEATURES, SYSTEM
from src.dataset import ASVSpoofDataset
from src.evaluate import score_split
from src.train_pytorch import build_model, resolve_dataset_root, resolve_protocol_file, pad_collate
from src.utils.metrics import compute_eer


def main():
    parser = argparse.ArgumentParser(description="Calibrate ASVspoof2019 LA threshold on dev split.")
    parser.add_argument("--checkpoint", type=str, required=True, help="Path to a trained .pth checkpoint")
    parser.add_argument(
        "--output",
        type=str,
        default="data/results/threshold.json",
        help="Path to save frozen threshold JSON",
    )
    parser.add_argument("--batch_size", type=int, default=8)
    parser.add_argument("--debug", action="store_true")
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
    dataset_root = resolve_dataset_root()
    protocol_file = resolve_protocol_file(dataset_root, "dev")
    processed_dir = Path(DATA["processed_dir"]).resolve()
    dataset = ASVSpoofDataset(str(protocol_file), str(processed_dir), debug=args.debug)
    loader = DataLoader(
        dataset,
        batch_size=args.batch_size,
        shuffle=False,
        num_workers=0,
        pin_memory=False,
        collate_fn=pad_collate,
    )

    model = None
    if model_name != "xgboost":
        model = build_model(model_name, input_dim=input_dim).to(device)
        state_dict = checkpoint.get("model_state_dict", checkpoint.get("state_dict"))
        if state_dict is None:
            raise KeyError("Checkpoint missing model_state_dict/state_dict")
        model.load_state_dict(state_dict)

    probabilities, labels = score_split(model, loader, device, use_amp, xgb_classifier=xgb_classifier)
    eer, threshold = compute_eer(probabilities, labels)

    output_path = Path(args.output)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    payload = {
        "model": model_name,
        "feature": feature_name,
        "threshold": float(threshold),
        "eer": float(eer),
        "split": "dev",
        "num_samples": int(len(labels)),
    }
    with open(output_path, "w", encoding="utf-8") as handle:
        json.dump(payload, handle, indent=2)

    # Write calibrated threshold back into checkpoint artifact as well.
    checkpoint["threshold"] = float(threshold)
    checkpoint["eer"] = float(eer)
    if checkpoint_path.suffix.lower() == ".pkl":
        with open(checkpoint_path, "wb") as handle:
            pickle.dump(checkpoint, handle)
    else:
        torch.save(checkpoint, checkpoint_path)

    print("=" * 80)
    print("Calibration complete")
    print(f"Model:      {model_name}")
    print(f"Feature:    {feature_name}")
    print(f"Samples:    {len(labels)}")
    print(f"EER:        {eer:.6f}")
    print(f"Threshold:  {threshold:.6f}")
    print(f"Saved to:   {output_path}")
    print("=" * 80)


if __name__ == "__main__":
    main()
