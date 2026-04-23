"""Audio preprocessing helpers for ASVspoof2019 LA."""

from __future__ import annotations

from pathlib import Path

import librosa
import numpy as np


def pre_emphasis(audio: np.ndarray, coefficient: float = 0.97) -> np.ndarray:
	"""Apply y[n] = x[n] - coefficient * x[n-1]."""
	audio = np.asarray(audio, dtype=np.float32)
	if audio.size == 0:
		return audio
	emphasized = np.empty_like(audio)
	emphasized[0] = audio[0]
	emphasized[1:] = audio[1:] - coefficient * audio[:-1]
	return emphasized


def truncate_or_pad(audio: np.ndarray, target_length: int) -> np.ndarray:
	"""Force a waveform to an exact length."""
	audio = np.asarray(audio, dtype=np.float32)
	if audio.size >= target_length:
		return audio[:target_length]
	return np.pad(audio, (0, target_length - audio.size), mode="constant")


def load_fixed_length_audio(audio_path: str | Path, sample_rate: int, max_duration: float) -> np.ndarray:
	"""Load mono audio and clamp it to a fixed duration."""
	audio, _ = librosa.load(str(audio_path), sr=sample_rate, mono=True, duration=max_duration)
	target_length = int(sample_rate * max_duration)
	return truncate_or_pad(audio, target_length)

