# Social Protocol (Escargot)

## Format compact (CBOR/JSON)
```json
{
  "type": "POST",
  "content": "<140",
  "channel": "#SafeZone",
  "duco_tx": {"amount": 0.01, "to": "wallet"},
  "sig": "ECDSA_Arduino"
}
```

## Types
- `POST` : message social court (<140 chars)
- `LIKE` : réaction (pouce, crâne)
- `DM` : message privé AES-256
- `MINE_SHARE` : share DUCO offloadé

## Gossip rules
- `hop_limit`: 3
- TTL: 600s
- Rejouer interdit (nonce + timestamp, signé Arduino)
