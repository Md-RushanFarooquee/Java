"""Metrics helpers for ASVspoof2019 LA evaluation."""

from __future__ import annotations

import numpy as np


def compute_log_loss(probabilities: np.ndarray, labels: np.ndarray, eps: float = 1e-7) -> float:
	"""Compute binary log loss from positive-class probabilities."""
	probabilities = np.asarray(probabilities, dtype=np.float64)
	labels = np.asarray(labels, dtype=np.float64)
	probabilities = np.clip(probabilities, eps, 1.0 - eps)
	loss = -(labels * np.log(probabilities) + (1.0 - labels) * np.log(1.0 - probabilities))
	return float(np.mean(loss))


def _far_frr_at_threshold(scores: np.ndarray, labels: np.ndarray, threshold: float) -> tuple[float, float]:
	"""Compute FAR and FRR at a given threshold.

	Higher scores are assumed to indicate spoof class.
	"""
	predictions = scores >= threshold
	labels = labels.astype(bool)

	positives = labels.sum()
	negatives = (~labels).sum()

	false_accepts = np.logical_and(predictions, ~labels).sum()
	false_rejects = np.logical_and(~predictions, labels).sum()

	far = false_accepts / max(int(negatives), 1)
	frr = false_rejects / max(int(positives), 1)
	return float(far), float(frr)


def compute_eer(scores: np.ndarray, labels: np.ndarray) -> tuple[float, float]:
	"""Compute EER and corresponding threshold using brute-force FAR/FRR sweep.

	Returns:
		(eer, threshold)
	"""
	scores = np.asarray(scores, dtype=np.float64)
	labels = np.asarray(labels, dtype=np.int64)

	unique_thresholds = np.unique(scores)
	if unique_thresholds.size == 0:
		return 0.0, 0.0

	thresholds = np.concatenate(
		(
			[unique_thresholds.min() - 1e-6],
			unique_thresholds,
			[unique_thresholds.max() + 1e-6],
		)
	)

	fars = []
	frrs = []
	for threshold in thresholds:
		far, frr = _far_frr_at_threshold(scores, labels, threshold)
		fars.append(far)
		frrs.append(frr)

	fars = np.asarray(fars, dtype=np.float64)
	frrs = np.asarray(frrs, dtype=np.float64)
	diffs = fars - frrs

	exact_matches = np.where(np.isclose(diffs, 0.0))[0]
	if exact_matches.size > 0:
		index = int(exact_matches[0])
		return float((fars[index] + frrs[index]) / 2.0), float(thresholds[index])

	crossing_indices = np.where(np.sign(diffs[:-1]) != np.sign(diffs[1:]))[0]
	if crossing_indices.size > 0:
		index = int(crossing_indices[0])
		x0, x1 = thresholds[index], thresholds[index + 1]
		y0, y1 = diffs[index], diffs[index + 1]
		alpha = abs(y0) / max(abs(y0) + abs(y1), 1e-12)
		threshold = x0 + alpha * (x1 - x0)
		eer = fars[index] + alpha * (fars[index + 1] - fars[index])
		return float(eer), float(threshold)

	best_index = int(np.argmin(np.abs(diffs)))
	best_threshold = thresholds[best_index]
	best_eer = (fars[best_index] + frrs[best_index]) / 2.0
	return float(best_eer), float(best_threshold)


def compute_accuracy_from_probabilities(probabilities: np.ndarray, labels: np.ndarray, threshold: float = 0.5) -> float:
	"""Compute accuracy using a fixed probability threshold."""
	probabilities = np.asarray(probabilities, dtype=np.float64)
	labels = np.asarray(labels, dtype=np.int64)
	predictions = (probabilities >= threshold).astype(np.int64)
	return float((predictions == labels).mean())

