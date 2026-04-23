@echo off
echo Launching Batch Feature Extraction...

python -m src.extract_features ^
    --feature lfcc ^
    --split all

pause
