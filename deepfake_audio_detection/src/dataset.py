import os
import torch
import numpy as np
from torch.utils.data import Dataset
from src.config import FEATURES


class ASVSpoofDataset(Dataset):
    def __init__(self, protocol_file, base_processed_dir, debug=False):
        self.data = []
        self.feature_type = FEATURES["type"]

        # Use mfcc/lfcc subfolder
        self.npy_dir = os.path.join(base_processed_dir, self.feature_type)

        with open(protocol_file, "r") as f:
            lines = f.readlines()

        if debug:
            lines = lines[:100]

        for line in lines:
            parts = line.strip().split()

            file_id = parts[1]
            label_str = parts[-1]   # robust
            label = 0 if label_str == "bonafide" else 1

            self.data.append({
                "file_id": file_id,
                "label": label
            })

    def __len__(self):
        return len(self.data)

    def __getitem__(self, idx):
        item = self.data[idx]

        npy_path = os.path.join(self.npy_dir, f"{item['file_id']}.npy")

        if not os.path.exists(npy_path):
            raise FileNotFoundError(f"Missing feature file: {npy_path}")

        feature = np.load(npy_path)

        feature = torch.tensor(feature, dtype=torch.float32)
        label = torch.tensor(item["label"], dtype=torch.long)

        return feature, label
