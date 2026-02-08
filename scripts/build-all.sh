#!/usr/bin/env bash
set -euo pipefail

# PlatformIO build: survive sans IDE lourde

( cd firmware/escargot-node && pio run )
( cd firmware/arduino-crypto-miner && pio run )
