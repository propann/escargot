/*
 * 🐌 ESCARGOT MINER - ARDUINO NANO
 * "Le travail c'est la santé, mais miner c'est la survie."
 *
 * Rôle : Esclave numérique dédié au minage de Duino-Coin.
 * Cible : Arduino Nano (ATmega328P)
 * Connexion : D2 (RX) / D3 (TX) vers ESP32
 */

#include <SoftwareSerial.h>

// Configuration du lien vital vers l'ESP32
// RX sur D2, TX sur D3. Ne pas inverser sinon l'Arduino parle dans le vide.
SoftwareSerial BridgeSerial(2, 3);

// Identité du survivant
const String DUCO_USER = "Escargot";

void setup() {
  // Debug USB (pour vérifier qu'il ne fume pas)
  Serial.begin(9600);
  
  // Canal de communication vers le monde extérieur (ESP32)
  BridgeSerial.begin(9600);
  
  pinMode(LED_BUILTIN, OUTPUT);
  
  Serial.println("BOOT: Initialisation du protocole de sueur...");
  Serial.println("STATUS: Pioche affûtée. Prêt à creuser.");
}

void loop() {
  // 1. Simulation de l'effort intense (Calcul de Hash SHA1)
  // L'Arduino donne tout ce qu'il a. Ses transistors hurlent en silence.
  // En réalité, on attend juste un peu, mais chut.
  delay(random(3000, 8000)); // Le temps de trouver une pépite dans la boue

  // 2. EUREKA ! On a trouvé quelque chose qui ressemble à de l'argent.
  unsigned long fakeHash = random(100000, 999999);
  
  // 3. Formatage du rapport de mission
  // Format: DUCO|User:Nom|Hash:Valeur
  String payload = "DUCO|User:" + DUCO_USER + "|Hash:" + String(fakeHash);
  
  // 4. Transmission au QG (ESP32)
  BridgeSerial.println(payload);
  
  // Log pour l'opérateur qui regarde l'écran noir
  Serial.print("💰 VICTOIRE: J'ai extrait ");
  Serial.print(fakeHash);
  Serial.println(" du sol numérique. Envoi au QG.");

  // 5. Célébration (La LED clignote de joie)
  digitalWrite(LED_BUILTIN, HIGH);
  delay(200);
  digitalWrite(LED_BUILTIN, LOW);
}