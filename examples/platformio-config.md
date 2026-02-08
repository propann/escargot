# PlatformIO Config (example)

## Custom Board Configs

```ini
[env:custom_esp32]
platform = espressif32
board = esp32doit-devkit-v1
framework = arduino
build_flags =
  -D LORA_PRESET_LONG_SLOW
  -D ESCARGOT_LOW_POWER

[env:custom_nano]
platform = atmelavr
board = nanoatmega328
framework = arduino
```
