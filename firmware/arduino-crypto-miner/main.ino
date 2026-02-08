#include <Arduino.h>

// Survive the horde: offload crypto + mining to Arduino

static void eccSign(const String &payload, String &outSig) {
  // TODO: TinyECC integration
  outSig = "SIG_STUB";
}

static void ducoS1Hash(const String &nonce, String &outShare) {
  // TODO: DUCO lightweight hash
  outShare = String("SHARE_") + nonce;
}

static void handlePing() {
  Serial.println("PONG");
}

void setup() {
  Serial.begin(115200);
}

void loop() {
  if (Serial.available()) {
    String cmd = Serial.readStringUntil('\n');

    if (cmd == "PING") {
      handlePing();
      return;
    }

    if (cmd.startsWith("SIGN_AND_MINE:")) {
      String payload = cmd.substring(strlen("SIGN_AND_MINE:"));

      String sig;
      eccSign(payload, sig);

      String share;
      ducoS1Hash("NONCE_STUB", share);

      Serial.print("SIG:");
      Serial.print(sig);
      Serial.print(":DUCO_SHARE:");
      Serial.println(share);
    }
  }
}
