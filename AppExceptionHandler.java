package com.propann.escargot.common;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Gestionnaire global d'exceptions pour éviter les crashs silencieux.
 * Capture toutes les erreurs non gérées dans les threads.
 */
public class AppExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(AppExceptionHandler.class.getName());

    public static void register() {
        Thread.setDefaultUncaughtExceptionHandler(new AppExceptionHandler());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.log(Level.SEVERE, "🔥 CRASH CRITIQUE NON GÉRÉ dans le thread [" + t.getName() + "]", e);
        // Logique de récupération ou d'arrêt propre
        System.err.println("Une erreur fatale est survenue. Vérifiez les logs pour plus de détails.");
    }
}