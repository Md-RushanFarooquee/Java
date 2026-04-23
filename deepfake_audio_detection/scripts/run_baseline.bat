@echo off
echo Launching XGBoost Baseline Training...

python -m src.train_baseline ^
    --feature lfcc

pause
