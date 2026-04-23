"""Final evaluation on the ASVspoof2019 LA eval split using a frozen threshold."""

from __future__ import annotations

import argparse
from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from src.evaluate import main as evaluate_main


def main():
    parser = argparse.ArgumentParser(description="Run final eval with a frozen threshold.")
    parser.add_argument("--checkpoint", type=str, required=True, help="Path to trained checkpoint")
    parser.add_argument(
        "--threshold_file",
        type=str,
        required=True,
        help="Path to threshold JSON produced by calibration",
    )
    parser.add_argument("--batch_size", type=int, default=8)
    parser.add_argument("--debug", action="store_true")
    parser.add_argument("--save_reports", action="store_true")
    args = parser.parse_args()

    if not Path(args.threshold_file).exists():
        raise FileNotFoundError(f"Threshold file not found: {args.threshold_file}")

    import sys

    sys.argv = [
        "evaluate.py",
        "--checkpoint",
        args.checkpoint,
        "--split",
        "eval",
        "--batch_size",
        str(args.batch_size),
        "--threshold_file",
        args.threshold_file,
    ]
    if args.save_reports:
        sys.argv.append("--save_reports")
    if args.debug:
        sys.argv.append("--debug")

    evaluate_main()


if __name__ == "__main__":
    main()
