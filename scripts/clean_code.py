from pathlib import Path
import re

ROOT = Path(__file__).resolve().parents[1]

patterns = [
    re.compile(r"//\s*(step|etapa|passo)\s*\d+.*", re.I),
    re.compile(r"//\s*(todo|nota|explicação):.*", re.I),
]

for path in ROOT.rglob("*.java"):
    text = path.read_text(encoding="utf-8")
    cleaned = text
    for pattern in patterns:
        cleaned = pattern.sub("", cleaned)
    if cleaned != text:
        path.write_text(cleaned, encoding="utf-8")
