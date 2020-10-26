package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Graph {
    private Map<Node, List<Node>> buren; 
    
    public Graph(){
        buren = new LinkedHashMap();
    }
    
    public Map<Node, List<Node>> getBuren(){
        return buren;
    }
    
    public void setBuren(Map<Node, List<Node>> buren){
        this.buren = buren;
    }

    public List<Node> getBurenByNode(Node node){
        return buren.get(node);
    }

    public void addMap(Node node){
        buren.putIfAbsent(node, new ArrayList());
    }

    public void addEdge(Edge edge){
        buren.get(edge.getStart()).add(edge.getEinde());
        buren.get(edge.getEinde()).add(edge.getEinde());
    }


    
    
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

    public Node getNodeByIndex(int index){
        Node result = null;
        for (Node n : nodes){
            if (n.getIndex() == index){
                result = n;
            }
        }
        return result;
    }
}
