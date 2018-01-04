package com.dpteam;

public class Edge implements Comparable<Edge> {


    private int id, startVertexId, endVertexId, digCost, value;

    public Edge(int id, int startVertexId, int endVertexId, int digCost, int value){
        this.id = id;
        this.startVertexId = startVertexId;
        this.endVertexId = endVertexId;
        this.digCost = digCost;
        this.value = value;
    }

    public int compareTo(Edge e){
        return this.value - e.value;
    }

    public int getId(){
        return id;
    }

    public int getValue(){
        return value;
    }

    public int getStartVertex(){
        return startVertexId;
    }

    public int getEndVertex() {
        return endVertexId;
    }

    public int getDigCost() { return digCost; }

}
