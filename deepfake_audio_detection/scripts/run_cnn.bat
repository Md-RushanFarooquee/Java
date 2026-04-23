@echo off
echo Launching CNN Training Pipeline...

python -m src.train_pytorch ^
    --model cnn ^
    --feature lfcc ^
    --batch_size 16 ^
    --epochs 50 ^
    --early_stopping_patience 10

pause
