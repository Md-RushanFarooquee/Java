"""Windows-friendly wrapper for LSTM training."""

import argparse
import sys
from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from src.train_pytorch import main as train_main


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--batch_size", type=int, default=8)
    parser.add_argument("--epochs", type=int, default=50)
    parser.add_argument("--early_stopping_patience", type=int, default=10)
    parser.add_argument("--debug", action="store_true")
    args = parser.parse_args()
    train_main(
        argparse.Namespace(
            model="lstm",
            feature="lfcc",
            batch_size=args.batch_size,
            epochs=args.epochs,
            early_stopping_patience=args.early_stopping_patience,
            debug=args.debug,
        )
    )
