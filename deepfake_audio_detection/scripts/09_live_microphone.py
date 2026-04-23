"""Live microphone demo for qualitative deepfake audio testing."""

from __future__ import annotations

import argparse
import pickle
from pathlib import Path
import sys

PROJECT_ROOT = Path(__file__).resolve().parents[1]
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

import numpy as np
import torch

try:
    import sounddevice as sd
except ImportError as exc:  # pragma: no cover - runtime dependency check
    sd = None
    _sd_import_error = exc

from src.config import DATA, FEATURES, SYSTEM
from src.extract_features import extract_lfcc, extract_mfcc
from src.train_pytorch import build_model
from src.utils.audio_utils import pre_emphasis, truncate_or_pad


def record_audio(seconds: float, sample_rate: int) -> np.ndarray:
    if sd is None:
        raise ImportError(
            "sounddevice is not installed. Install requirements before using the live microphone demo."
        ) from _sd_import_error

    print(f"Recording for {seconds:.1f} seconds... Speak now.")
    audio = sd.rec(int(seconds * sample_rate), samplerate=sample_rate, channels=1, dtype="float32")
    sd.wait()
    return audio.squeeze(-1)


def extract_feature(audio: np.ndarray, feature_type: str, sample_rate: int) -> np.ndarray:
    audio = truncate_or_pad(audio.astype(np.float32), int(sample_rate * DATA["max_duration"]))
    audio = pre_emphasis(audio)
    if feature_type == "mfcc":
        return extract_mfcc(audio, sample_rate)
    if feature_type == "lfcc":
        return extract_lfcc(audio, sample_rate)
    raise ValueError(f"Unsupported feature type: {feature_type}")


def predict_clip(model_path: Path, feature_type: str, seconds: float, sample_rate: int):
    if model_path.suffix.lower() == ".pkl":
        with open(model_path, "rb") as handle:
            checkpoint = pickle.load(handle)
    else:
        checkpoint = torch.load(model_path, map_location="cpu")

    model_name = checkpoint["model"]
    input_dim = int(checkpoint.get("input_dim", 0))
    classifier = checkpoint.get("classifier") if model_name == "xgboost" else None

    audio = record_audio(seconds, sample_rate)
    feature = extract_feature(audio, feature_type, sample_rate)
    device = torch.device(SYSTEM["device"] if torch.cuda.is_available() else "cpu")

    if classifier is not None:
        spoof_probability = float(classifier.predict_proba(feature.reshape(1, -1))[0, 1])
    else:
        feature_tensor = torch.tensor(feature, dtype=torch.float32).unsqueeze(0)
        lengths = torch.tensor([feature_tensor.shape[1]], dtype=torch.long)
        model = build_model(model_name, input_dim=input_dim).to(device)
        state_dict = checkpoint.get("model_state_dict", checkpoint.get("state_dict"))
        if state_dict is None:
            raise KeyError("Checkpoint missing model_state_dict/state_dict")
        model.load_state_dict(state_dict)
        model.eval()

        with torch.no_grad():
            logits = model(feature_tensor.to(device), lengths.to(device))
            probabilities = torch.softmax(logits, dim=1)[0]

        spoof_probability = float(probabilities[1].item())
    if "threshold" not in checkpoint:
        raise KeyError("Checkpoint missing saved threshold. Run dev calibration first.")
    threshold = float(checkpoint["threshold"])
    decision = "spoof" if spoof_probability >= threshold else "bonafide"

    print("=" * 80)
    print(f"Model:            {model_name}")
    print(f"Feature:          {feature_type}")
    print(f"Spoof probability: {spoof_probability:.6f}")
    print(f"Threshold:        {threshold:.6f}")
    print(f"Decision:         {decision}")
    print("=" * 80)


def main():
    parser = argparse.ArgumentParser(description="Live microphone spoof detection demo.")
    parser.add_argument("--checkpoint", type=str, required=True, help="Trained checkpoint (.pth)")
    parser.add_argument("--feature", type=str, default=FEATURES["type"], choices=["mfcc", "lfcc"])
    parser.add_argument("--seconds", type=float, default=3.0, help="Recording duration")
    args = parser.parse_args()

    predict_clip(Path(args.checkpoint), args.feature, args.seconds, DATA["sample_rate"])


if __name__ == "__main__":
    main()
