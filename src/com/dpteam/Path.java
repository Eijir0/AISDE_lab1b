package com.dpteam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path {

    private int fibresNeeded;
    private List<Integer> verticesId = new ArrayList<>();
    private List<Integer> edgesId = new ArrayList<>();
    private List<Pair> fibreNumByEdgesId = new ArrayList<>();

    public List<Integer> getVerticesId() {
        return verticesId;
    }

    public List<Integer> getEdgesId() {
        return edgesId;
    }

    public List<Pair> getFibreNumByEdgesId() {
        return fibreNumByEdgesId;
    }

    public int getFibresNeeded() { return fibresNeeded; }

    public void setFibresNeeded(int fibresNeeded) { this.fibresNeeded = fibresNeeded; }

    public Pair newPair(Integer left, int right){
        return new Pair(left, right);
    }

    public void addFibresById(int left, int right) {
        Iterator<Pair> iterator = fibreNumByEdgesId.iterator();
        while (iterator.hasNext()) {
            Pair p = iterator.next();
            if(p.getLeft() == left)
                p.addRight(right);
        }
    }

    public int findById(int id){
        for (Pair p: fibreNumByEdgesId) {
            if(p.left == id)
                return p.right;
        }
        return 0;
    }

    public class Pair {
        private int left;
        private int right;

        public Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public int getLeft() {
            return left;
        }
        public int getRight() {
            return right;
        }

        public void addRight(int val) { this.right += val; }

    }

}
