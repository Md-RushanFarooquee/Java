# deepfake_audio_detection

Resource-constrained deepfake audio detection project structure.

## Execution

- 0. Verify dataset: `scripts/run_verify.bat`
- 1. Extract features: `scripts/run_feature_extraction.bat`
- 2. Train baseline: `scripts/run_baseline.bat`
- 3. Calibrate dev threshold: `scripts/run_calibration.bat`
- 4. Final eval: `scripts/run_eval.bat`
- 5. CNN: `scripts/run_cnn.bat`
- 6. LSTM: `scripts/run_lstm.bat`
- 7. CRNN: `scripts/run_crnn.bat`
- 8. Batch inference: `scripts/run_inference.bat`
- 9. Live microphone demo: `scripts/run_mic.bat`
- 10. Generate leaderboard/index: `scripts/run_leaderboard.bat`
- 11. Full pipeline orchestration: `scripts/run_all.bat`

## Notes

- notebooks/ is EDA and debugging only (no training).
- src/ contains runnable pipeline modules.
- data/raw and data/processed are gitignored.
- Final eval/inference can export attack-wise reports (CSV/JSON) under `data/results/`.
- Final eval with reports also writes a markdown summary to `experiments/`, including hardest/easiest attack rankings.
- Summary/report artifacts now include run metadata (UTC timestamp, checkpoint path, and command) for reproducibility.
- Leaderboard generation writes `experiments/leaderboard.md`, `experiments/leaderboard.csv`, and `experiments/README.md`.
- `experiments/leaderboard.md` includes both overall ranking and per-split sections (`DEV`, `EVAL`) when data exists.
