package com.dpteam;

public class Cable {

    private int id, capacity, cost;

    public Cable(int id, int capacity, int cost) {
        this.id = id;
        this.capacity = capacity;
        this.cost = cost;
    }

    public int getId() { return id; }

    public int getCapacity() {
        return capacity;
    }

    public int getCost() {
        return cost;
    }
}
