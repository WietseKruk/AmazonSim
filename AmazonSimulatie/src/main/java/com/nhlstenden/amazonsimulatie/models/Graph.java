package com.nhlstenden.amazonsimulatie.models;

import java.util.List;

public class Graph {
    private final List<Node> vertexes;
    private final List<Edge> edges;

    public Graph(List<Node> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Node> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Node getNodeById(String id) {
        Node result = null;
        // DEBUG: int attempt = 1;
        for (Node n : vertexes) {
            if (n.getId().equals(id)) {
                result = n;
                //DEBUG: System.out.println(result.getId() + " Found during ATTEMPT: " + attempt);
                //DEBUG: attempt++;
            } else {
                //DEBUG: System.out.println("-Can't find Node with name: " + id + " CURRENT: " +
                //DEBUG: n.getId() + " -ATTEMPT: " + attempt);
                //DEBUG: attempt++;
            }
        }

        return result;
    }

    // DEBUG
    public void printAllNodes() {
        for (Node n : vertexes) {
            System.out.println("NODE: " + n.getId() + " X: " + n.getX() + " Z: " + n.getZ());
        }
    }
}