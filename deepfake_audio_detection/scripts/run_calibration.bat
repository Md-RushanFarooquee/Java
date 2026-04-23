@echo off
echo Calibrating dev threshold...

python scripts\06_calibrate_threshold.py ^
    --checkpoint checkpoints\xgboost_lfcc.pkl ^
    --output data\results\threshold.json

pause
