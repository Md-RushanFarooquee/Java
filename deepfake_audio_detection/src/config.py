import os

DATA = {
    "raw_dir": "data/raw/",
    "processed_dir": "data/processed/",
    "sample_rate": 16000,
    "max_duration": 3.0
}

FEATURES = {
    "type": "lfcc",              # switch to "mfcc" anytime
    "save_dir": "data/processed/",
    "n_lfcc": 20,
    "n_mfcc": 20,
    "n_fft": 512,
    "hop_length": 256
}

TRAINING = {
    "cnn_batch_size": 16,
    "lstm_batch_size": 8,
    "accumulation_steps": 4,
    "learning_rate": 1e-4,
    "epochs": 50
}

SYSTEM = {
    "use_amp": True,
    "device": "cuda"
}

# Ensure folders exist
os.makedirs(FEATURES["save_dir"], exist_ok=True)
