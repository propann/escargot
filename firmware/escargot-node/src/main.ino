#include <Arduino.h>
#include <SoftwareSerial.h>

// Survive horde: low-power mining
// Stub pour le mineur Arduino

SoftwareSerial Bridge(2, 3); // RX, TX

void setup() {
    Serial.begin(9600);
    Bridge.begin(9600);
    Serial.println("⛏️ MINER: Ready to dig graves and coins.");
}

void mineDuco() {
    // Stub: Simulation de minage SHA1
    // En vrai: DuinoCoin.work()
    delay(100); 
}

void signMessage(String msg) {
    // Stub: Signature ECDSA (TinyECC)
    Bridge.println("SIG:xyz123_valid_zombie_proof");
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