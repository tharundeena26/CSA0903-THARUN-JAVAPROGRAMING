#!/bin/sh
set -eu
mkdir -p data
for size in 1 10 50; do
  file="data/attendance-${size}mb.log"
  if [ ! -f "$file" ]; then
    yes "attendance log entry" | head -c "$((size * 1024 * 1024))" > "$file" || true
  fi
  echo "=== ${size} MB ==="
  java LineCounter "$file"
done
