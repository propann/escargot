#!/bin/bash
# Build All Firmwares - Zombie Proof Check
set -e

echo "[ESCARGOT] Building ESP32 Host Firmware..."
cd firmware/escargot-node
pio run
cd ../..

echo "[ESCARGOT] Building Arduino Enclave Firmware..."
cd firmware/arduino-crypto-miner
pio run
cd ../..

echo "[SUCCESS] All systems ready for apocalypse."