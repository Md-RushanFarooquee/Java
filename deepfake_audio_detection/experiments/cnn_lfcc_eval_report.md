# ASVspoof2019 LA Evaluation Report

## Overall

- Split: eval
- Model: cnn
- Feature: lfcc
- Samples: 100
- EER: 0.465116
- Threshold: 0.557991
- Threshold Source: file:data\results\cnn_lfcc_threshold.json
- Log Loss: 0.616608
- Accuracy: 0.470000

## Run Metadata

- Evaluated At (UTC): 2026-04-23T00:14:39Z
- Checkpoint: C:\Coding\College Projects\DeepFakeProject\deepfake_audio_detection\checkpoints\cnn_lfcc.pth
- Command: evaluate.py --checkpoint checkpoints/cnn_lfcc.pth --split eval --batch_size 8 --threshold_file data/results/cnn_lfcc_threshold.json --save_reports --debug

## Attack-Wise Breakdown

| Attack | #Bonafide | #Spoof | EER | Accuracy | TP | FP | FN | TN | FPR | FNR |
|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| A07 | 14 | 6 | 0.3333 | 0.7000 | 4 | 4 | 2 | 10 | 0.2857 | 0.3333 |
| A08 | 14 | 8 | 0.5000 | 0.5000 | 4 | 7 | 4 | 7 | 0.5000 | 0.5000 |
| A09 | 14 | 7 | 0.2857 | 0.7143 | 5 | 4 | 2 | 10 | 0.2857 | 0.2857 |
| A10 | 14 | 9 | 0.3333 | 0.6957 | 6 | 4 | 3 | 10 | 0.2857 | 0.3333 |
| A11 | 14 | 4 | 0.5000 | 0.5000 | 2 | 7 | 2 | 7 | 0.5000 | 0.5000 |
| A12 | 14 | 7 | 0.2857 | 0.7143 | 5 | 4 | 2 | 10 | 0.2857 | 0.2857 |
| A13 | 14 | 10 | 0.4000 | 0.6250 | 6 | 5 | 4 | 9 | 0.3571 | 0.4000 |
| A14 | 14 | 6 | 0.5000 | 0.5000 | 3 | 7 | 3 | 7 | 0.5000 | 0.5000 |
| A15 | 14 | 5 | 0.5714 | 0.4211 | 2 | 8 | 3 | 6 | 0.5714 | 0.6000 |
| A16 | 14 | 8 | 0.6250 | 0.4091 | 3 | 8 | 5 | 6 | 0.5714 | 0.6250 |
| A17 | 14 | 7 | 0.5714 | 0.4286 | 3 | 8 | 4 | 6 | 0.5714 | 0.5714 |
| A18 | 14 | 7 | 0.7143 | 0.2857 | 2 | 10 | 5 | 4 | 0.7143 | 0.7143 |
| A19 | 14 | 2 | 0.5000 | 0.5000 | 1 | 7 | 1 | 7 | 0.5000 | 0.5000 |

## Hardest Attacks

Top 5 by EER:
- A18: EER=0.7143, FNR=0.7143, FPR=0.7143, Accuracy=0.2857
- A16: EER=0.6250, FNR=0.6250, FPR=0.5714, Accuracy=0.4091
- A15: EER=0.5714, FNR=0.6000, FPR=0.5714, Accuracy=0.4211
- A17: EER=0.5714, FNR=0.5714, FPR=0.5714, Accuracy=0.4286
- A08: EER=0.5000, FNR=0.5000, FPR=0.5000, Accuracy=0.5000

Top 5 by FNR:
- A18: FNR=0.7143, EER=0.7143, FPR=0.7143, Accuracy=0.2857
- A16: FNR=0.6250, EER=0.6250, FPR=0.5714, Accuracy=0.4091
- A15: FNR=0.6000, EER=0.5714, FPR=0.5714, Accuracy=0.4211
- A17: FNR=0.5714, EER=0.5714, FPR=0.5714, Accuracy=0.4286
- A08: FNR=0.5000, EER=0.5000, FPR=0.5000, Accuracy=0.5000

## Easiest Attacks

Top 5 by lowest EER:
- A09: EER=0.2857, FNR=0.2857, FPR=0.2857, Accuracy=0.7143
- A12: EER=0.2857, FNR=0.2857, FPR=0.2857, Accuracy=0.7143
- A07: EER=0.3333, FNR=0.3333, FPR=0.2857, Accuracy=0.7000
- A10: EER=0.3333, FNR=0.3333, FPR=0.2857, Accuracy=0.6957
- A13: EER=0.4000, FNR=0.4000, FPR=0.3571, Accuracy=0.6250
