package com.dpteam;

public class Main {

    public static void main(String[] args) {

        Network net = new Network();
        net.readNetwork("lab.txt");
        net.useAlgorithm();
        System.out.println();
        net.showNet();

    }
}
