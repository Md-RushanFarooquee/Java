@echo off
echo Launching LSTM Training Pipeline...

python -m src.train_pytorch ^
    --model lstm ^
    --feature lfcc ^
    --batch_size 8 ^
    --epochs 50 ^
    --early_stopping_patience 10

pause
