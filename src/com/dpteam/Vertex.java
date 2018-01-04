package com.dpteam;

public class Vertex {

    private int id, x, y, customerNum;

    public Vertex(int id, int x, int y, int customerNum){

        this.id = id;
        this.x = x;
        this.y = y;
        this.customerNum = customerNum;
    }

    public int getId(){
        return id;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getCustomerNum() { return customerNum; }
}
