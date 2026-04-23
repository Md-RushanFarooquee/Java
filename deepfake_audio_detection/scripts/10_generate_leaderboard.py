"""Generate cross-run leaderboard and report index from evaluation artifacts."""

from __future__ import annotations

import argparse
import csv
import json
from datetime import datetime, timezone
from pathlib import Path


def parse_summary_file(path: Path) -> dict | None:
    try:
        with open(path, "r", encoding="utf-8") as handle:
            payload = json.load(handle)
    except (OSError, json.JSONDecodeError):
        return None

    required = ["model", "feature", "split", "eer", "threshold", "log_loss", "accuracy"]
    if any(key not in payload for key in required):
        return None

    record = {
        "summary_file": str(path).replace("\\", "/"),
        "model": str(payload["model"]),
        "feature": str(payload["feature"]),
        "split": str(payload["split"]),
        "num_samples": int(payload.get("num_samples", 0)),
        "eer": float(payload["eer"]),
        "threshold": float(payload["threshold"]),
        "threshold_source": str(payload.get("threshold_source", "unknown")),
        "log_loss": float(payload["log_loss"]),
        "accuracy": float(payload["accuracy"]),
        "evaluated_at_utc": str(payload.get("evaluated_at_utc", "")),
        "checkpoint_path": str(payload.get("checkpoint_path", "")),
        "command": str(payload.get("command", "")),
    }

    report_name = f"{record['model']}_{record['feature']}_{record['split']}_report.md"
    report_path = Path("experiments") / report_name
    if report_path.exists():
        record["report_file"] = str(report_path).replace("\\", "/")
    else:
        record["report_file"] = ""

    return record


def save_leaderboard_csv(rows: list[dict], output_path: Path) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    fieldnames = [
        "model",
        "feature",
        "split",
        "num_samples",
        "eer",
        "accuracy",
        "log_loss",
        "threshold",
        "threshold_source",
        "evaluated_at_utc",
        "summary_file",
        "report_file",
    ]
    with open(output_path, "w", newline="", encoding="utf-8") as handle:
        writer = csv.DictWriter(handle, fieldnames=fieldnames)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: row.get(key, "") for key in fieldnames})


def save_leaderboard_markdown(rows: list[dict], output_path: Path) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    lines = []
    lines.append("# Evaluation Leaderboard")
    lines.append("")
    lines.append(f"Generated at (UTC): {datetime.now(timezone.utc).strftime('%Y-%m-%dT%H:%M:%SZ')}")
    lines.append("")

    if not rows:
        lines.append("No summary files found under data/results.")
    else:
        lines.append("## Overall Ranking")
        lines.append("")
        lines.append("| Rank | Model | Feature | Split | EER | Accuracy | Log Loss | Threshold | Evaluated At | Summary | Report |")
        lines.append("|---:|---|---|---|---:|---:|---:|---:|---|---|---|")
        for idx, row in enumerate(rows, start=1):
            summary = row["summary_file"]
            report = row["report_file"] if row["report_file"] else "n/a"
            lines.append(
                "| "
                f"{idx} | {row['model']} | {row['feature']} | {row['split']} | "
                f"{row['eer']:.6f} | {row['accuracy']:.6f} | {row['log_loss']:.6f} | "
                f"{row['threshold']:.6f} | {row.get('evaluated_at_utc', '')} | {summary} | {report} |"
            )

        for split in ("dev", "eval"):
            split_rows = [row for row in rows if row.get("split") == split]
            if not split_rows:
                continue
            lines.append("")
            lines.append(f"## {split.upper()} Ranking")
            lines.append("")
            lines.append("| Rank | Model | Feature | EER | Accuracy | Log Loss | Threshold | Evaluated At | Summary | Report |")
            lines.append("|---:|---|---|---:|---:|---:|---:|---|---|---|")
            for idx, row in enumerate(split_rows, start=1):
                summary = row["summary_file"]
                report = row["report_file"] if row["report_file"] else "n/a"
                lines.append(
                    "| "
                    f"{idx} | {row['model']} | {row['feature']} | "
                    f"{row['eer']:.6f} | {row['accuracy']:.6f} | {row['log_loss']:.6f} | "
                    f"{row['threshold']:.6f} | {row.get('evaluated_at_utc', '')} | {summary} | {report} |"
                )

    with open(output_path, "w", encoding="utf-8") as handle:
        handle.write("\n".join(lines) + "\n")


def save_experiments_index(rows: list[dict], output_path: Path) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    lines = []
    lines.append("# Experiments Index")
    lines.append("")
    lines.append("## Generated Artifacts")
    lines.append("")
    lines.append("- leaderboard.md: Cross-run ranking (sorted by lowest EER)")
    lines.append("- leaderboard.csv: Machine-readable leaderboard")
    lines.append("")
    lines.append("## Reports")
    lines.append("")

    report_paths = sorted({row["report_file"] for row in rows if row.get("report_file")})
    if not report_paths:
        lines.append("No markdown reports found yet.")
    else:
        for report_path in report_paths:
            lines.append(f"- {report_path}")

    lines.append("")
    lines.append("## Summaries")
    lines.append("")
    summary_paths = sorted({row["summary_file"] for row in rows})
    if not summary_paths:
        lines.append("No summary JSON files found yet.")
    else:
        for summary_path in summary_paths:
            lines.append(f"- {summary_path}")

    with open(output_path, "w", encoding="utf-8") as handle:
        handle.write("\n".join(lines) + "\n")


def main() -> None:
    parser = argparse.ArgumentParser(description="Generate leaderboard and experiment index artifacts.")
    parser.add_argument(
        "--results_dir",
        type=str,
        default="data/results",
        help="Directory containing *_summary.json files.",
    )
    parser.add_argument(
        "--experiments_dir",
        type=str,
        default="experiments",
        help="Output directory for leaderboard and index artifacts.",
    )
    args = parser.parse_args()

    results_dir = Path(args.results_dir)
    experiments_dir = Path(args.experiments_dir)
    experiments_dir.mkdir(parents=True, exist_ok=True)

    summary_files = sorted(results_dir.glob("*_summary.json")) if results_dir.exists() else []
    rows = []
    for summary_file in summary_files:
        row = parse_summary_file(summary_file)
        if row is not None:
            rows.append(row)

    rows.sort(key=lambda record: (record["eer"], -record["accuracy"], record["model"], record["feature"]))

    leaderboard_csv = experiments_dir / "leaderboard.csv"
    leaderboard_md = experiments_dir / "leaderboard.md"
    experiments_index = experiments_dir / "README.md"

    save_leaderboard_csv(rows, leaderboard_csv)
    save_leaderboard_markdown(rows, leaderboard_md)
    save_experiments_index(rows, experiments_index)

    print(f"Summaries parsed: {len(rows)}")
    print(f"Leaderboard CSV:  {leaderboard_csv}")
    print(f"Leaderboard MD:   {leaderboard_md}")
    print(f"Index README:     {experiments_index}")


if __name__ == "__main__":
    main()
