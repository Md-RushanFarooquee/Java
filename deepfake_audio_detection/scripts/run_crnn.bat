@echo off
echo Launching CRNN Training Pipeline...

python -m src.train_pytorch ^
    --model crnn ^
    --feature lfcc ^
    --batch_size 4 ^
    --epochs 50 ^
    --early_stopping_patience 10

pause
