package com.nhlstenden.amazonsimulatie.graph;

import java.util.*;

public class Node2 {

    private String naam;
    private int x;
    private int z;
    private int value;
    private Map<Node2, Integer> connectedNodes = new HashMap<>();
    private boolean isStellage;
    private int index;

    public Node2(String naam, int index, boolean isStellage, int x, int z, int value){
        this.naam = naam;
        this.x = x;
        this.z = z;
        this.value = value;
        this.isStellage = isStellage;
        this.index = index;
    }

    public boolean isStellage(){
        return isStellage;
    }

    public int getIndex(){
        return index;
    }

    public String getNaam(){
        return naam;
    }

    public int getValue(){
        return value;
    }

    public int getX(){
        return x;
    }
    
    public int getZ(){
        return z;
    }

    public Map<Node2, Integer> getConnectedNodes(){
        return connectedNodes;
    }

    public void addConnectedNode(Node2 connectedNode, int value){
        connectedNodes.put(connectedNode, value);
    }  
}
