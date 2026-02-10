package com.propann.escargot.graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GraphOfThoughts {
    
    private final Graph<SearchNode, DefaultEdge> graph;
    private final Stack<SearchNode> executionStack;
    private SearchNode root;

    public GraphOfThoughts() {
        // Création d'un graphe orienté simple
        this.graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        this.executionStack = new Stack<>();
        
        // Nœud racine
        this.root = new SearchNode("Start", 0, false);
        this.graph.addVertex(root);
        this.executionStack.push(root);
    }

    /**
     * Appelé quand le solveur fait un choix (Branching)
     */
    public void onDecision(String decisionLabel) {
        if (executionStack.isEmpty()) return;
        SearchNode parent = executionStack.peek();
        SearchNode child = new SearchNode(decisionLabel, parent.depth + 1, false);
        
        graph.addVertex(child);
        graph.addEdge(parent, child);
        executionStack.push(child);
    }

    /**
     * Appelé quand le solveur réalise qu'il est dans une impasse (Fail)
     */
    public void onFail(String reason) {
        if (executionStack.isEmpty()) return;
        SearchNode parent = executionStack.peek();
        SearchNode failNode = new SearchNode("FAIL: " + reason, parent.depth + 1, true);
        
        graph.addVertex(failNode);
        graph.addEdge(parent, failNode);
        
        // On ne push pas le fail dans la stack car on va tout de suite backtracker
    }

    /**
     * Appelé quand le solveur remonte dans l'arbre (Backtrack)
     */
    public void onBacktrack() {
        if (!executionStack.isEmpty()) {
            executionStack.pop();
        }
    }

    /**
     * Exporte le graphe au format DOT (lisible par Graphviz)
     */
    public String exportToDOT() {
        DOTExporter<SearchNode, DefaultEdge> exporter = new DOTExporter<>(v -> String.valueOf(v.id));
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new HashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            // Colorie les échecs en rouge, le reste en bleu
            if (v.isFail) {
                map.put("color", DefaultAttribute.createAttribute("red"));
                map.put("style", DefaultAttribute.createAttribute("filled"));
                map.put("fillcolor", DefaultAttribute.createAttribute("#ffcccc"));
            }
            return map;
        });

        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        return writer.toString();
    }
}