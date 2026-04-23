"""Verify ASVspoof2019 LA dataset integrity and reproducibility assumptions."""

from __future__ import annotations

import os
import random
from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

import numpy as np
import torch

from src.config import DATA

EXPECTED_COUNTS = {
    "train": 25380,
    "dev": 24844,
    "eval": 71237,
}

EXPECTED_LABELS = {"bonafide": 0, "spoof": 1}


def seed_everything(seed: int = 42):
    os.environ["PYTHONHASHSEED"] = str(seed)
    random.seed(seed)
    np.random.seed(seed)
    torch.manual_seed(seed)
    if torch.cuda.is_available():
        torch.cuda.manual_seed_all(seed)
        torch.backends.cudnn.deterministic = True
        torch.backends.cudnn.benchmark = False


def resolve_dataset_root() -> Path:
    candidates = [Path("ASVspoof2019_LA"), Path(DATA["raw_dir"]).resolve()]
    for candidate in candidates:
        if candidate.exists() and (candidate / "ASVspoof2019_LA_cm_protocols").exists():
            return candidate.resolve()
    raise FileNotFoundError("Could not locate ASVspoof2019_LA dataset root.")


def protocol_file(dataset_root: Path, split: str) -> Path:
    protocol_dir = dataset_root / "ASVspoof2019_LA_cm_protocols"
    mapping = {
        "train": protocol_dir / "ASVspoof2019.LA.cm.train.trn.txt",
        "dev": protocol_dir / "ASVspoof2019.LA.cm.dev.trl.txt",
        "eval": protocol_dir / "ASVspoof2019.LA.cm.eval.trl.txt",
    }
    return mapping[split]


def parse_line(line: str):
    parts = line.strip().split()
    if len(parts) < 5:
        raise ValueError(f"Invalid protocol line: {line}")
    return parts[1], parts[-1], parts[3]


def verify_protocol(split: str, root: Path) -> bool:
    file_path = protocol_file(root, split)
    if not file_path.exists():
        print(f"✗ Missing protocol file: {file_path}")
        return False

    with open(file_path, "r", encoding="utf-8") as handle:
        lines = handle.readlines()

    expected = EXPECTED_COUNTS[split]
    actual = len(lines)
    ok = actual == expected
    status = "✓" if ok else "✗"
    print(f"{status} {split.upper()}: {actual} lines (expected {expected})")

    labels = []
    attacks = set()
    for line in lines:
        _, label_str, attack_id = parse_line(line)
        labels.append(EXPECTED_LABELS[label_str])
        if attack_id != "-":
            attacks.add(attack_id)

    bonafide = labels.count(EXPECTED_LABELS["bonafide"])
    spoof = labels.count(EXPECTED_LABELS["spoof"])
    print(f"  Labels: bonafide={bonafide}, spoof={spoof}")
    print(f"  Attacks: {sorted(attacks)}")

    if split == "eval" and attacks != set([f"A{i:02d}" for i in range(7, 20)]):
        print("  ✗ Eval split attack coverage does not match A07-A19")
        ok = False

    return ok


def main():
    seed_everything(42)
    root = resolve_dataset_root()
    print("=" * 80)
    print("ASVspoof2019 LA Dataset Integrity Check")
    print(f"Dataset root: {root}")
    print("=" * 80)

    overall = True
    for split in ["train", "dev", "eval"]:
        overall = verify_protocol(split, root) and overall

    print("=" * 80)
    print("✓ All checks passed." if overall else "✗ One or more checks failed.")
    print("=" * 80)


if __name__ == "__main__":
    main()
