"""Batch inference entrypoint using a trained checkpoint and saved threshold."""

from __future__ import annotations

import argparse
from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from src.evaluate import main as evaluate_main


def main():
    parser = argparse.ArgumentParser(
        description="Run batch inference using an ASVspoof2019 LA checkpoint with saved threshold."
    )
    parser.add_argument("--checkpoint", type=str, required=True, help="Path to trained checkpoint")
    parser.add_argument(
        "--threshold_file",
        type=str,
        default=None,
        help="Optional frozen threshold JSON file (required only if checkpoint lacks threshold)",
    )
    parser.add_argument("--split", type=str, default="eval", choices=["dev", "eval"])
    parser.add_argument("--batch_size", type=int, default=8)
    parser.add_argument("--debug", action="store_true")
    parser.add_argument("--save_reports", action="store_true")
    args = parser.parse_args()

    if args.threshold_file is not None and not Path(args.threshold_file).exists():
        raise FileNotFoundError(f"Threshold file not found: {args.threshold_file}")

    import sys

    sys.argv = [
        "evaluate.py",
        "--checkpoint",
        args.checkpoint,
        "--split",
        args.split,
        "--batch_size",
        str(args.batch_size),
    ]
    if args.threshold_file is not None:
        sys.argv.extend(["--threshold_file", args.threshold_file])
    if args.save_reports:
        sys.argv.append("--save_reports")
    if args.debug:
        sys.argv.append("--debug")

    evaluate_main()


if __name__ == "__main__":
    main()
