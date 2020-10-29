package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
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

    List<Node> nodes = new ArrayList<>();

    public void addNode(Node node){
        nodes.add(node);
    }

    public Node getNodeByName(String name){
        Node result = null;
        for (Node n : nodes){
            if (n.getName().equals(name)){
                result = n;
            }
        }
        return result;
    }
}