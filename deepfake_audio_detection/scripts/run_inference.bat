@echo off
echo Running batch inference...

python scripts\08_inference_batch.py ^
    --checkpoint checkpoints\xgboost_lfcc.pkl ^
    --threshold_file data\results\threshold.json ^
    --save_reports

pause
