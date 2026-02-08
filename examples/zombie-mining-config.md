# Zombie Mining Config (example)

```yaml
node:
  id: escargot-01
  channel: "#Ravitaillement"
  wallet: "DUCO_WALLET_ID_STUB"
  rewards:
    per_like: 0.01
    per_relay: 0.01
    per_post: 0.02
  mining:
    mode: offload-arduino
    power_budget: low
crypto:
  arduino_uart: /dev/ttyS1
  ecdsa: true
  aes256: true
mesh:
  gossip:
    hop_limit: 3
    ttl_seconds: 600
```
