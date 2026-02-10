#include <Arduino.h>
#include <SoftwareSerial.h>

// Survive horde: low-power mining
// Mineur Arduino Nano - Esclave Crypto V3

// RX, TX pour debug ou comms secondaires si besoin
// La comm principale avec l'ESP32 se fait via Serial hardware (0, 1) sur le Nano
// ou via SoftwareSerial si on veut garder l'USB pour le debug.
// Ici on assume SoftwareSerial pour laisser l'USB libre pour le flash.
SoftwareSerial Bridge(2, 3); // RX=D2, TX=D3

void setup() {
    Serial.begin(9600); // Debug USB
    Bridge.begin(9600); // Link to ESP32
    Serial.println("⛏️ MINER V3: Ready to dig graves and coins.");
}

void mineDuco() {
    // Stub: Simulation de minage SHA1 (DUCO-S1)
    // En vrai: DuinoCoin.work()
    // Survive EMP: offline opti - Low power delay
    delay(50); 
}

void signMessage(String msg) {
    // Stub: Signature ECDSA (TinyECC)
    // Simule une signature cryptographique pour valider l'identité du survivant
    Bridge.println("SIG:ecdsa_valid_" + String(millis()));
}

void loop() {
    if (Bridge.available()) {
        String cmd = Bridge.readStringUntil('\n');
        if (cmd.startsWith("SIGN|")) {
            signMessage(cmd.substring(5));
        }
    }
    mineDuco();
}