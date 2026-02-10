package com.propann.escargot.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.propann.escargot.core.SmartDomain;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Chargeur de puzzle sécurisé (Mission 5).
 * Remplace toute désérialisation native par du parsing JSON strict.
 */
public class PuzzleLoader {

    private static final Logger LOGGER = Logger.getLogger(PuzzleLoader.class.getName());
    private static final int MAX_VARIABLES = 10_000; // Protection contre DoS mémoire
    private static final int MAX_DOMAIN_SIZE = 1_000_000;

    private final ObjectMapper mapper;

    public PuzzleLoader() {
        this.mapper = new ObjectMapper();
        // Jackson est sécurisé par défaut contre les attaques de type polymorphisme si on n'active pas defaultTyping
    }

    /**
     * Charge un fichier puzzle JSON avec validation stricte.
     * @param jsonFile Le fichier à charger.
     * @return Le réseau de contraintes construit.
     */
    public SmartDomain[] load(File jsonFile) {
        try {
            if (jsonFile == null || !jsonFile.exists()) {
                throw new IllegalArgumentException("Fichier introuvable.");
            }

            // 1. Parsing JSON sécurisé
            JsonNode root = mapper.readTree(jsonFile);

            // 2. Validation de la structure
            if (!root.has("variables") || !root.get("variables").isArray()) {
                throw new SecurityException("Format de fichier invalide : 'variables' manquant ou incorrect.");
            }

            int varCount = root.get("variables").size();
            validateLimits(varCount);

            SmartDomain[] variables = new SmartDomain[varCount];

            // 3. Chargement sécurisé des domaines
            for (int i = 0; i < varCount; i++) {
                JsonNode varNode = root.get("variables").get(i);
                variables[i] = parseDomain(varNode);
            }
            return variables;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur I/O lors de la lecture du puzzle : " + jsonFile.getName(), e);
            throw new RuntimeException("Echec du chargement du fichier", e);
        } catch (SecurityException e) {
            LOGGER.log(Level.WARNING, "ALERTE SÉCURITÉ : Fichier puzzle malformé ou malveillant.", e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erreur inattendue dans le loader.", e);
            throw new RuntimeException("Erreur système critique", e);
        }
    }

    private void validateLimits(int varCount) {
        if (varCount > MAX_VARIABLES) {
            throw new SecurityException("Puzzle rejeté : Trop de variables (" + varCount + " > " + MAX_VARIABLES + ")");
        }
        if (varCount <= 0) {
             throw new SecurityException("Puzzle rejeté : Aucune variable définie.");
        }
    }

    private SmartDomain parseDomain(JsonNode node) {
        SmartDomain domain = new SmartDomain();
        if (node.isArray()) {
            for (JsonNode val : node) {
                int v = val.asInt();
                if (v < 0 || v > MAX_DOMAIN_SIZE) {
                    throw new SecurityException("Valeur hors limites détectée dans le domaine : " + v);
                }
                domain.add(v);
            }
        }
        return domain;
    }
}