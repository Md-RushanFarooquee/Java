@echo off
echo Running live microphone demo...

python scripts\09_live_microphone.py ^
    --checkpoint checkpoints\xgboost_lfcc.pkl ^
    --feature lfcc ^
    --seconds 3.0

pause
