#include <Arduino.h>

// Survive horde: low-bandwidth opti
// Stub pour le noeud principal ESP32

#define ARDUINO_RX_PIN 16
#define ARDUINO_TX_PIN 17

void setup() {
    Serial.begin(115200);
    Serial1.begin(9600, SERIAL_8N1, ARDUINO_RX_PIN, ARDUINO_TX_PIN);
    
    Serial.println("🐌 ESCARGOT NODE: Online");
}

void sendPost(String content) {
    // 1. Demander signature à l'Arduino
    Serial1.println("SIGN|" + content);
    
    // 2. Attendre réponse (Stub)
    // String signature = Serial1.readStringUntil('\n');
    
    // 3. Broadcast sur le Mesh
    // mesh.broadcast(content + "|" + signature);
    Serial.println("Broadcasting: " + content);
}

void loop() {
    // Ecoute du Mesh et du Serial
    // if (mesh.available()) { ... }
    delay(100);
}