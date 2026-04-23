"""Batch feature extraction entrypoint: audio -> MFCC/LFCC -> .npy."""

import argparse
from pathlib import Path

import librosa
import numpy as np

from src.config import DATA, FEATURES
from src.utils.audio_utils import load_fixed_length_audio, pre_emphasis


def resolve_dataset_root() -> Path:
    """Find the dataset root in the current workspace."""
    candidates = [
        Path(DATA["raw_dir"]).resolve(),
        Path("ASVspoof2019_LA").resolve(),
    ]

    for candidate in candidates:
        if candidate.exists() and (candidate / "ASVspoof2019_LA_cm_protocols").exists():
            return candidate

    raise FileNotFoundError(
        "Could not locate dataset root. Expected data/raw or ASVspoof2019_LA."
    )


def resolve_protocol_file(dataset_root: Path, split: str) -> Path:
    """Return the official CM protocol file for a split."""
    protocol_dir = dataset_root / "ASVspoof2019_LA_cm_protocols"
    mapping = {
        "train": protocol_dir / "ASVspoof2019.LA.cm.train.trn.txt",
        "dev": protocol_dir / "ASVspoof2019.LA.cm.dev.trl.txt",
        "eval": protocol_dir / "ASVspoof2019.LA.cm.eval.trl.txt",
    }
    return mapping[split]


def resolve_audio_dir(dataset_root: Path, split: str) -> Path:
    """Return the FLAC directory for a split."""
    mapping = {
        "train": dataset_root / "ASVspoof2019_LA_train" / "flac",
        "dev": dataset_root / "ASVspoof2019_LA_dev" / "flac",
        "eval": dataset_root / "ASVspoof2019_LA_eval" / "flac",
    }
    return mapping[split]


def parse_protocol_line(line: str):
    """Parse one CM protocol row."""
    parts = line.strip().split()
    if len(parts) < 5:
        raise ValueError(f"Invalid protocol line: {line}")

    speaker_id = parts[0]
    file_id = parts[1]
    attack_id = parts[3]
    label = 0 if parts[4] == "bonafide" else 1
    return speaker_id, file_id, attack_id, label


def _linear_filterbank(sample_rate: int, n_fft: int, n_filters: int) -> np.ndarray:
    """Build a simple linear filterbank for LFCC extraction."""
    n_freqs = n_fft // 2 + 1
    filterbank = np.zeros((n_filters, n_freqs), dtype=np.float32)

    freq_bins = np.linspace(0, n_freqs - 1, n_filters + 2)
    for i in range(1, n_filters + 1):
        left = int(np.floor(freq_bins[i - 1]))
        center = int(np.floor(freq_bins[i]))
        right = int(np.floor(freq_bins[i + 1]))

        if center == left:
            center = left + 1
        if right == center:
            right = center + 1

        for j in range(left, center):
            filterbank[i - 1, j] = (j - left) / max(center - left, 1)
        for j in range(center, right):
            filterbank[i - 1, j] = (right - j) / max(right - center, 1)

    return filterbank


def extract_mfcc(audio: np.ndarray, sample_rate: int) -> np.ndarray:
    """Extract MFCC features with fixed parameters."""
    mfcc = librosa.feature.mfcc(
        y=audio,
        sr=sample_rate,
        n_mfcc=FEATURES["n_mfcc"],
        n_fft=FEATURES["n_fft"],
        hop_length=FEATURES["hop_length"],
    )
    return mfcc.T.astype(np.float32)


def extract_lfcc(audio: np.ndarray, sample_rate: int) -> np.ndarray:
    """Extract LFCC features using a linear filterbank + DCT."""
    stft = librosa.stft(
        audio,
        n_fft=FEATURES["n_fft"],
        hop_length=FEATURES["hop_length"],
        window="hann",
    )
    power_spec = np.abs(stft) ** 2

    filterbank = _linear_filterbank(sample_rate, FEATURES["n_fft"], FEATURES["n_lfcc"])
    linear_energies = np.dot(filterbank, power_spec)
    linear_energies = np.maximum(linear_energies, 1e-10)
    log_energies = np.log(linear_energies)

    lfcc = librosa.feature.mfcc(S=log_energies, n_mfcc=FEATURES["n_lfcc"])
    return lfcc.T.astype(np.float32)


def process_split(split: str, dataset_root: Path, processed_dir: Path, debug: bool = False):
    """Extract and save features for one dataset split."""
    protocol_file = resolve_protocol_file(dataset_root, split)
    audio_dir = resolve_audio_dir(dataset_root, split)
    save_dir = processed_dir / FEATURES["type"]
    save_dir.mkdir(parents=True, exist_ok=True)

    with open(protocol_file, "r", encoding="utf-8") as handle:
        lines = handle.readlines()

    if debug:
        lines = lines[:100]

    print(f"\n[{split.upper()}] {len(lines)} files | saving to {save_dir}")

    for index, line in enumerate(lines, start=1):
        _, file_id, _, _ = parse_protocol_line(line)
        audio_path = audio_dir / f"{file_id}.flac"
        output_path = save_dir / f"{file_id}.npy"

        if output_path.exists() and not debug:
            continue

        audio = load_fixed_length_audio(
            audio_path,
            sample_rate=DATA["sample_rate"],
            max_duration=DATA["max_duration"],
        )
        audio = pre_emphasis(audio)

        if FEATURES["type"] == "mfcc":
            feature = extract_mfcc(audio, DATA["sample_rate"])
        elif FEATURES["type"] == "lfcc":
            feature = extract_lfcc(audio, DATA["sample_rate"])
        else:
            raise ValueError(f"Unsupported feature type: {FEATURES['type']}")

        np.save(output_path, feature)

        if index % 500 == 0 or index == len(lines):
            print(f"  Processed {index}/{len(lines)} -> {output_path.name}")


def main():
    parser = argparse.ArgumentParser(description="Batch feature extraction for ASVspoof2019 LA")
    parser.add_argument(
        "--feature",
        type=str,
        default=FEATURES["type"],
        choices=["mfcc", "lfcc"],
        help="Feature type to extract",
    )
    parser.add_argument(
        "--split",
        type=str,
        default="all",
        choices=["train", "dev", "eval", "all"],
        help="Dataset split to process",
    )
    parser.add_argument(
        "--debug",
        action="store_true",
        help="Only process first 100 samples for smoke testing",
    )
    args = parser.parse_args()

    FEATURES["type"] = args.feature

    dataset_root = resolve_dataset_root()
    processed_dir = Path(DATA["processed_dir"]).resolve()
    processed_dir.mkdir(parents=True, exist_ok=True)

    splits = [args.split] if args.split != "all" else ["train", "dev", "eval"]

    print("=" * 80)
    print("ASVspoof2019 LA Batch Feature Extraction")
    print("=" * 80)
    print(f"Dataset root: {dataset_root}")
    print(f"Feature type:  {FEATURES['type']}")
    print(f"Sample rate:   {DATA['sample_rate']} Hz")
    print(f"Max duration:  {DATA['max_duration']} sec")
    print(f"N FFT:         {FEATURES['n_fft']}")
    print(f"Hop length:    {FEATURES['hop_length']}")
    print(f"Output dir:    {processed_dir / FEATURES['type']}")

    for split in splits:
        process_split(split, dataset_root, processed_dir, debug=args.debug)

    print("\nDone.")


if __name__ == "__main__":
    main()
