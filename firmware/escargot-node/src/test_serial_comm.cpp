#include <Arduino.h>
#include <unity.h>

// Configuration Pinout (Doit matcher schematics.md)
#define PIN_RX_ARDUINO 16
#define PIN_TX_ARDUINO 17
#define ARDUINO_BAUD 9600

HardwareSerial EnclaveSerial(1);

void setUp(void) {
    // Exécuté avant chaque test
}

void tearDown(void) {
    // Exécuté après chaque test
}

/**
 * Test: Vérifie que l'Arduino répond "PONG_ALIVE" à un "PING".
 * Vital pour s'assurer que le module crypto n'a pas été détruit ou déconnecté.
 */
void test_enclave_connection_ping_pong(void) {
    // 1. Vider le buffer RX (au cas où des débris de données traînent)
    while(EnclaveSerial.available()) {
        EnclaveSerial.read();
    }

    // 2. Envoyer la commande PING
    EnclaveSerial.println("PING");

    // 3. Attendre la réponse (Timeout 2s - large pour un Arduino potentiellement endormi)
    String response = "";
    unsigned long start = millis();
    bool received = false;
    
    while (millis() - start < 2000) {
        if (EnclaveSerial.available()) {
            response = EnclaveSerial.readStringUntil('\n');
            response.trim(); // Nettoyer \r\n
            if (response.length() > 0) {
                received = true;
                break;
            }
        }
        delay(10);
    }

    // 4. Assertions Unity
    TEST_ASSERT_TRUE_MESSAGE(received, "Timeout: L'enclave Arduino ne repond pas (Check wiring/battery)");
    TEST_ASSERT_EQUAL_STRING("PONG_ALIVE", response.c_str());
}

void setup() {
    delay(2000); // Laisser le temps au board de boot
    
    Serial.begin(115200); // Pour le rapport de test Unity
    EnclaveSerial.begin(ARDUINO_BAUD, SERIAL_8N1, PIN_RX_ARDUINO, PIN_TX_ARDUINO);

    UNITY_BEGIN();
    RUN_TEST(test_enclave_connection_ping_pong);
    UNITY_END();
}

void loop() {
    // Rien à faire ici, Unity gère tout dans setup()
}
