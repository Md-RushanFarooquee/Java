@echo off
echo Running final evaluation...

python scripts\07_eval_final.py ^
    --checkpoint checkpoints\xgboost_lfcc.pkl ^
    --threshold_file data\results\threshold.json ^
    --save_reports

pause
