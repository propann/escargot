package com.propann.escargot.debug;

import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.search.loop.monitors.IMonitorContradiction;
import org.chocosolver.solver.search.loop.monitors.IMonitorDownBranch;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.loop.monitors.IMonitorUpBranch;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Observateur "Graph of Thoughts" pour le débogage logique.
 * <p>
 * Cette classe écoute les événements du solveur (Choco) et construit un graphe dirigé
 * représentant l'exploration de l'espace de recherche.
 * </p>
 * Utile pour visualiser les "backtracks" et comprendre les boucles infinies.
 */
public class SearchGraphObserver implements IMonitorDownBranch, IMonitorUpBranch, IMonitorSolution, IMonitorContradiction {

    // Le graphe de pensée (GoT)
    private final Graph<String, DefaultEdge> searchGraph;
    
    // Pile pour suivre le chemin courant dans l'arbre de recherche
    private final Stack<String> nodeStack;
    
    // Compteur pour générer des IDs uniques pour les nœuds
    private int nodeCounter = 0;

    public SearchGraphObserver() {
        this.searchGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.nodeStack = new Stack<>();
        
        // Création de la racine
        String rootId = "ROOT";
        searchGraph.addVertex(rootId);
        nodeStack.push(rootId);
    }

    // --- Événements Choco Solver ---

    @Override
    public void onDownBranch(boolean left) {
        // Le solveur descend dans l'arbre (prend une décision)
        String parentId = nodeStack.peek();
        String childId = "NODE_" + (++nodeCounter) + (left ? "_L" : "_R");
        
        searchGraph.addVertex(childId);
        searchGraph.addEdge(parentId, childId);
        
        nodeStack.push(childId);
    }

    @Override
    public void onUpBranch() {
        // Le solveur remonte (backtrack)
        if (!nodeStack.isEmpty()) {
            nodeStack.pop();
        }
    }

    @Override
    public void onContradiction(ContradictionException k) {
        // Le solveur a rencontré une impasse
        if (!nodeStack.isEmpty()) {
            String currentId = nodeStack.peek();
            // On marque le nœud comme "FAIL" (dans une vraie implémentation, on stockerait des attributs)
            // Ici, on ajoute juste un nœud feuille explicite pour visualiser l'échec
            String failId = currentId + "_FAIL";
            searchGraph.addVertex(failId);
            searchGraph.addEdge(currentId, failId);
        }
    }

    @Override
    public void onSolution() {
        // Une solution a été trouvée
        if (!nodeStack.isEmpty()) {
            String currentId = nodeStack.peek();
            String solId = currentId + "_SOLUTION";
            searchGraph.addVertex(solId);
            searchGraph.addEdge(currentId, solId);
        }
    }

    // --- Export ---

    /**
     * Exporte le graphe de recherche au format DOT (Graphviz).
     * @return Une chaîne contenant la description du graphe.
     */
    public String exportToDOT() {
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> {
            // Nettoyage de l'ID pour DOT (alphanumérique uniquement)
            return v.replaceAll("[^a-zA-Z0-9_]", "");
        });

        // Ajout de styles pour visualiser les types de nœuds
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            if (v.contains("FAIL")) {
                map.put("color", DefaultAttribute.createAttribute("red"));
                map.put("style", DefaultAttribute.createAttribute("filled"));
            } else if (v.contains("SOLUTION")) {
                map.put("color", DefaultAttribute.createAttribute("green"));
                map.put("style", DefaultAttribute.createAttribute("filled"));
            }
            map.put("label", DefaultAttribute.createAttribute(v));
            return map;
        });

        Writer writer = new StringWriter();
        exporter.exportGraph(searchGraph, writer);
        return writer.toString();
    }
}