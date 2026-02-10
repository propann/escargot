#include <Arduino.h>

// Survive horde: low-bandwidth opti
// Noeud principal ESP32 - Orchestrateur

#define ARDUINO_RX_PIN 16
#define ARDUINO_TX_PIN 17
#define BAUD_RATE_MINER 9600

void setup() {
    Serial.begin(115200);
    Serial1.begin(9600, SERIAL_8N1, ARDUINO_RX_PIN, ARDUINO_TX_PIN);
    
    Serial.println("🐌 ESCARGOT NODE: Online");
}

void sendPost(String content) {
    // Survive EMP: offline opti
    Serial.println("📝 Preparing post: " + content);

    // 1. Demander signature à l'Arduino
    Serial1.println("SIGN|" + content);
    
    // 2. Attendre réponse (Timeout 2s pour éviter blocage zombie)
    unsigned long start = millis();
    while (!Serial1.available() && millis() - start < 2000) { delay(10); }
    
    if (Serial1.available()) {
        String signature = Serial1.readStringUntil('\n');
        Serial.println("📡 Broadcasting with SIG: " + signature);
        // mesh.broadcast(content + "|" + signature);
    } else {
        Serial.println("❌ Error: Miner dead or eaten.");
    }
}

void loop() {
    // Ecoute du Mesh et du Serial
    // if (mesh.available()) { ... }
    delay(100);
}