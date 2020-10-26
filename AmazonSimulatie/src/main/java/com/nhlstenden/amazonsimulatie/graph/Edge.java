package com.nhlstenden.amazonsimulatie.graph;

public class Edge {
    private Node start;
    private Node einde;
    private int kosten; 

    public Edge(Node start, Node einde, int kosten){
        this.start = start;
        this.einde = einde;
        this.kosten = kosten;
    }

    public Node getStart(){
        return start;
    }

    public void setStart(Node start){
        this.start = start;
    }
    
    public Node getEinde(){
        return einde;
    }

    public void setEinde(Node einde){
        this.einde = einde;
    }

    public int getKosten(){
        return kosten;
    }

    public void setKosten(int kosten){
        this.kosten = kosten; 
    }
    
}
