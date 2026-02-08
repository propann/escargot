#!/usr/bin/env bash
set -euo pipefail

# Survive the blackout: offline queue, sync on relay
BROKER_HOST=${BROKER_HOST:-"localhost"}
BROKER_PORT=${BROKER_PORT:-1883}
QUEUE_FILE=${QUEUE_FILE:-"/tmp/escargot-duco-queue.json"}

if [[ ! -f "$QUEUE_FILE" ]]; then
  echo "[]" > "$QUEUE_FILE"
fi

echo "[escargot] Syncing DUCO shares via MQTT bridge..."
echo "Broker: ${BROKER_HOST}:${BROKER_PORT}"

# TODO: replace with real mqtt_pub (mosquitto_pub)
cat "$QUEUE_FILE" > /dev/null
sleep 1

echo "[escargot] Sync complete (stub)."
