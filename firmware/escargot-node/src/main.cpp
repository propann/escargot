// PlatformIO build: survive sans IDE lourde
#include <Arduino.h>
#include "hash.h" // DUCO lightweight hash stub

// Survive EMP: offline mining only, no heavy compute on ESP32
static const uint8_t MAX_POST_LEN = 140;

HardwareSerial ArduinoSerial(1);

// Minimal placeholders for Meshtastic-like mesh APIs
struct Mesh {
  void begin() {}
  void broadcast(const String &payload) {
    (void)payload;
  }
  void update() {}
};

static Mesh mesh;

static void pingArduino() {
  ArduinoSerial.println("PING");
  delay(10);
}

void sendSocialPost(String content, String channel) {
  if (content.length() > MAX_POST_LEN) {
    content = content.substring(0, MAX_POST_LEN);
  }

  String cmd = String("SIGN_AND_MINE:") + content + ":" + channel;
  ArduinoSerial.println(cmd);

  // Read reply (SIG + DUCO share)
  String reply = ArduinoSerial.readStringUntil('\n');

  // Build compact payload with Arduino signature
  String payload = String("{type:POST,content:'") + content +
                   String("',channel:'") + channel +
                   String("',arduino:'") + reply + String("'}");

  // Broadcast via mesh gossip
  mesh.broadcast(payload);
}

void setup() {
  Serial.begin(115200);
  ArduinoSerial.begin(115200, SERIAL_8N1, 16, 17);

  pingArduino();
  mesh.begin();
}

void loop() {
  mesh.update();

  // No heavy compute here — Arduino handles mining/crypto
  delay(1000); // Low-power idle
}
