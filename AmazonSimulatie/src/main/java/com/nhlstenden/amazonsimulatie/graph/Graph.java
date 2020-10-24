package com.nhlstenden.amazonsimulatie.graph;
import java.util.*;

public class Graph {
    List<Node> nodes = new ArrayList<>();

    public void addNode(Node node){
        nodes.add(node);
    }

    public List<Node> getGraph(){
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

    public Node getNodeByName(String name){
        Node result = null;
        for (Node n : nodes){
            if (n.getNaam().equals(name)){
                result = n;
            }
        }
        return result;
    }
}
