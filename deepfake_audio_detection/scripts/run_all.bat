@echo off
echo Running full ASVspoof2019 LA pipeline...

echo [1/6] Verify dataset
python scripts\00_verify_dataset.py

echo [2/6] Extract features
python scripts\01_extract_features.py --feature lfcc --split all

echo [3/6] Train baseline
python -m src.train_baseline --feature lfcc

echo [4/6] Calibrate threshold
python scripts\06_calibrate_threshold.py --checkpoint checkpoints\xgboost_lfcc.pkl --output data\results\threshold.json

echo [5/6] Final evaluation
python scripts\07_eval_final.py --checkpoint checkpoints\xgboost_lfcc.pkl --threshold_file data\results\threshold.json --save_reports

echo [6/6] Generate leaderboard and experiment index
python scripts\10_generate_leaderboard.py

pause
