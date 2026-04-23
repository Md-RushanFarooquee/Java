# ASVspoof2019 LA Evaluation Report

## Overall

- Split: eval
- Model: crnn
- Feature: lfcc
- Samples: 100
- EER: 0.571429
- Threshold: 0.463644
- Threshold Source: file:data\results\crnn_lfcc_threshold.json
- Log Loss: 0.899399
- Accuracy: 0.190000

## Run Metadata

- Evaluated At (UTC): 2026-04-23T00:14:46Z
- Checkpoint: C:\Coding\College Projects\DeepFakeProject\deepfake_audio_detection\checkpoints\crnn_lfcc.pth
- Command: evaluate.py --checkpoint checkpoints/crnn_lfcc.pth --split eval --batch_size 8 --threshold_file data/results/crnn_lfcc_threshold.json --save_reports --debug

## Attack-Wise Breakdown

| Attack | #Bonafide | #Spoof | EER | Accuracy | TP | FP | FN | TN | FPR | FNR |
|---|---:|---:|---:|---:|---:|---:|---:|---:|---:|---:|
| A07 | 14 | 6 | 0.5714 | 0.4000 | 2 | 8 | 4 | 6 | 0.5714 | 0.6667 |
| A08 | 14 | 8 | 0.6250 | 0.4091 | 3 | 8 | 5 | 6 | 0.5714 | 0.6250 |
| A09 | 14 | 7 | 0.5714 | 0.4286 | 3 | 8 | 4 | 6 | 0.5714 | 0.5714 |
| A10 | 14 | 9 | 0.5714 | 0.3913 | 3 | 8 | 6 | 6 | 0.5714 | 0.6667 |
| A11 | 14 | 4 | 0.5000 | 0.5000 | 2 | 7 | 2 | 7 | 0.5000 | 0.5000 |
| A12 | 14 | 7 | 0.5714 | 0.4286 | 3 | 8 | 4 | 6 | 0.5714 | 0.5714 |
| A13 | 14 | 10 | 0.5714 | 0.4167 | 4 | 8 | 6 | 6 | 0.5714 | 0.6000 |
| A14 | 14 | 6 | 0.3571 | 0.6000 | 3 | 5 | 3 | 9 | 0.3571 | 0.5000 |
| A15 | 14 | 5 | 0.4000 | 0.6316 | 3 | 5 | 2 | 9 | 0.3571 | 0.4000 |
| A16 | 14 | 8 | 0.4286 | 0.5455 | 4 | 6 | 4 | 8 | 0.4286 | 0.5000 |
| A17 | 14 | 7 | 0.4286 | 0.5714 | 4 | 6 | 3 | 8 | 0.4286 | 0.4286 |
| A18 | 14 | 7 | 0.3571 | 0.6190 | 4 | 5 | 3 | 9 | 0.3571 | 0.4286 |
| A19 | 14 | 2 | 0.7143 | 0.2500 | 0 | 10 | 2 | 4 | 0.7143 | 1.0000 |

## Hardest Attacks

Top 5 by EER:
- A19: EER=0.7143, FNR=1.0000, FPR=0.7143, Accuracy=0.2500
- A08: EER=0.6250, FNR=0.6250, FPR=0.5714, Accuracy=0.4091
- A07: EER=0.5714, FNR=0.6667, FPR=0.5714, Accuracy=0.4000
- A09: EER=0.5714, FNR=0.5714, FPR=0.5714, Accuracy=0.4286
- A10: EER=0.5714, FNR=0.6667, FPR=0.5714, Accuracy=0.3913

Top 5 by FNR:
- A19: FNR=1.0000, EER=0.7143, FPR=0.7143, Accuracy=0.2500
- A07: FNR=0.6667, EER=0.5714, FPR=0.5714, Accuracy=0.4000
- A10: FNR=0.6667, EER=0.5714, FPR=0.5714, Accuracy=0.3913
- A08: FNR=0.6250, EER=0.6250, FPR=0.5714, Accuracy=0.4091
- A13: FNR=0.6000, EER=0.5714, FPR=0.5714, Accuracy=0.4167

## Easiest Attacks

Top 5 by lowest EER:
- A14: EER=0.3571, FNR=0.5000, FPR=0.3571, Accuracy=0.6000
- A18: EER=0.3571, FNR=0.4286, FPR=0.3571, Accuracy=0.6190
- A15: EER=0.4000, FNR=0.4000, FPR=0.3571, Accuracy=0.6316
- A16: EER=0.4286, FNR=0.5000, FPR=0.4286, Accuracy=0.5455
- A17: EER=0.4286, FNR=0.4286, FPR=0.4286, Accuracy=0.5714
