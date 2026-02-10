package com.propann.escargot.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO (Data Transfer Object) pour le chargement sécurisé des puzzles.
 * Utilise Jackson pour mapper le JSON.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore les champs inconnus pour éviter les erreurs de parsing
public class PuzzleInput {
    public String name;
    public VariableDTO[] variables;

    public static class VariableDTO {
        public int id;
        public int domainMin;
        public int domainMax;
    }
}