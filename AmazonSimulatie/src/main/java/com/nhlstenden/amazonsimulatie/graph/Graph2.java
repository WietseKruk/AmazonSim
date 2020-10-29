package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Graph2 {
    private Map<Node2, List<Node2>> buren; 
    
    public Graph2(){
        buren = new LinkedHashMap();
    }
    
    public Map<Node2, List<Node2>> getBuren(){
        return buren;
    }
    
    public void setBuren(Map<Node2, List<Node2>> buren){
        this.buren = buren;
    }

    public List<Node2> getBurenByNode(Node2 node){
        return buren.get(node);
    }

    public void addMap(Node2 node){
        buren.putIfAbsent(node, new ArrayList());
    }

    public void addEdge(Edge2 edge){
        buren.get(edge.getStart()).add(edge.getEinde());
        buren.get(edge.getEinde()).add(edge.getStart());
    }


    
    
    List<Node2> nodes = new ArrayList<>();

    public void addNode(Node2 node){
        nodes.add(node);
    }

    public List<Node2> getGraph(){
        return nodes;
    }

    public Node2 getNode(int x, int z){
        Node2 result = null;
        for (Node2 n : nodes){
            if(n.getX() == x && n.getZ() == z){
                result = n;
                break;
            }    
        }
        return result;
    }

    public Node2 getNodeByName(String name){
        Node2 result = null;
        for (Node2 n : nodes){
            if (n.getNaam().equals(name)){
                result = n;
            }
        }
        return result;
    }

    public Node2 getNodeByIndex(int index){
        Node2 result = null;
        for (Node2 n : nodes){
            if (n.getIndex() == index){
                result = n;
            }
        }
        return result;
    }
}
