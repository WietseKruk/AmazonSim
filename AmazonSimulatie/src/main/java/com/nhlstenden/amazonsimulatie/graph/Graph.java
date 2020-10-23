package com.nhlstenden.amazonsimulatie.graph;
import java.util.*;

public class Graph {
    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node node){
        nodes.add(node);
    }

    public Set<Node> getGraph(){
        return nodes;
    }

    public Node getNode(int x, int z){
        Node result = null;
        for (Node n : nodes){
            if(n.getX() == x && n.getZ() == z){
                result = n;
                break;
            }    
        }
        return result;
    }
}
