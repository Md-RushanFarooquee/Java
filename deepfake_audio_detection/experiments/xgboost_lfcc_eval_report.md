# ASVspoof2019 LA Evaluation Report

## Overall

- Split: eval
- Model: xgboost
- Feature: lfcc
- Samples: 100
- EER: 0.500000
- Threshold: 0.000002
- Threshold Source: file:data\results\threshold.json
- Log Loss: 11.881339
- Accuracy: 0.140000

## Run Metadata

- Evaluated At (UTC): 2026-04-22T18:17:50Z
- Checkpoint: C:\Coding\College Projects\DeepFakeProject\deepfake_audio_detection\checkpoints\xgboost_lfcc.pkl
- Command: evaluate.py --checkpoint checkpoints/xgboost_lfcc.pkl --split eval --batch_size 8 --threshold_file data/results/threshold.json --save_reports --debug

## Attack-Wise Breakdown

| Attack | #Bonafide | #Spoof | EER | Accuracy | TP | FP | FN | TN | FPR | FNR |
|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| A07 | 14 | 6 | 0.5000 | 0.7000 | 0 | 0 | 6 | 14 | 0.0000 | 1.0000 |
| A08 | 14 | 8 | 0.5000 | 0.6364 | 0 | 0 | 8 | 14 | 0.0000 | 1.0000 |
| A09 | 14 | 7 | 0.5000 | 0.6667 | 0 | 0 | 7 | 14 | 0.0000 | 1.0000 |
| A10 | 14 | 9 | 0.5000 | 0.6087 | 0 | 0 | 9 | 14 | 0.0000 | 1.0000 |
| A11 | 14 | 4 | 0.5000 | 0.7778 | 0 | 0 | 4 | 14 | 0.0000 | 1.0000 |
| A12 | 14 | 7 | 0.5000 | 0.6667 | 0 | 0 | 7 | 14 | 0.0000 | 1.0000 |
| A13 | 14 | 10 | 0.5000 | 0.5833 | 0 | 0 | 10 | 14 | 0.0000 | 1.0000 |
| A14 | 14 | 6 | 0.5000 | 0.7000 | 0 | 0 | 6 | 14 | 0.0000 | 1.0000 |
| A15 | 14 | 5 | 0.5000 | 0.7368 | 0 | 0 | 5 | 14 | 0.0000 | 1.0000 |
| A16 | 14 | 8 | 0.5000 | 0.6364 | 0 | 0 | 8 | 14 | 0.0000 | 1.0000 |
| A17 | 14 | 7 | 0.5000 | 0.6667 | 0 | 0 | 7 | 14 | 0.0000 | 1.0000 |
| A18 | 14 | 7 | 0.5000 | 0.6667 | 0 | 0 | 7 | 14 | 0.0000 | 1.0000 |
| A19 | 14 | 2 | 0.5000 | 0.8750 | 0 | 0 | 2 | 14 | 0.0000 | 1.0000 |

## Hardest Attacks

Top 5 by EER:
- A07: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.7000
- A08: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.6364
- A09: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.6667
- A10: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.6087
- A11: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.7778

Top 5 by FNR:
- A07: FNR=1.0000, EER=0.5000, FPR=0.0000, Accuracy=0.7000
- A08: FNR=1.0000, EER=0.5000, FPR=0.0000, Accuracy=0.6364
- A09: FNR=1.0000, EER=0.5000, FPR=0.0000, Accuracy=0.6667
- A10: FNR=1.0000, EER=0.5000, FPR=0.0000, Accuracy=0.6087
- A11: FNR=1.0000, EER=0.5000, FPR=0.0000, Accuracy=0.7778

## Easiest Attacks

Top 5 by lowest EER:
- A07: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.7000
- A08: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.6364
- A09: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.6667
- A10: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.6087
- A11: EER=0.5000, FNR=1.0000, FPR=0.0000, Accuracy=0.7778
