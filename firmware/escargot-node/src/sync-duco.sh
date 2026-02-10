#!/bin/bash
# Sync DUCO rewards when internet is briefly available (e.g. satellite uplink)

echo "🔄 SYNC: Checking for internet..."

if ping -c 1 google.com &> /dev/null; then
    echo "🌐 ONLINE: Uploading mined hashes..."
    # python3 scripts/duco_upload.py --db escargot.db
else
    echo "💀 OFFLINE: Storing hashes locally."
fi