package com.dpteam;

import org.graphstream.graph.*;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.sync.SourceTime;
import org.graphstream.ui.view.Viewer;


import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;


public class Network {
    private Vertex [] vertices;
        private int main;
        private ArrayList<Integer> optional = new ArrayList<>();
        private ArrayList<Vertex> target = new ArrayList<>();
    private Edge [] edges;
    private Cable [] cables;
    private ArrayList<Path> paths = new ArrayList<>();
    private Path nett = null;
    private final static int INF = Integer.MAX_VALUE/2;

    private String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "   size: 15px;" +
                    "   text-size: 20 ;"+
                    "}" +
                    "node.marked {" +
                    "	fill-color: blue;" +
                    "   text-color: blue ;"+
                    "}"+
                    "edge {" +
                    "  fill-color: black;" +
                    "text-size: 20 ;"+
                    "}" +
                    "edge.marked {" +
                    "  fill-color: blue;" +
                    "   text-color: blue ;"+
                    "   size: 3px;"+
                    "}";



    public void readNetwork(String fileName){
        String line;

        try{
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int nV = 0 , iV = 0, nE = 0, iE = 0, nC = 0, iC = 0;
            while((line = bufferedReader.readLine()) != null) {
                if(line.startsWith("#"))
                    continue;

                if(line.contains("WEZLY")){
                    vertices = new Vertex[takeNumOf(line)];
                    nV = vertices.length;
                    iV =0;
                    continue;
                }

                if( nV > iV){
                    vertices[iV] = updateVertex(line);
                    iV++;
                    continue;
                }

                if(line.contains("KRAWEDZIE")){
                    edges = new Edge[takeNumOf(line)];
                    nE = edges.length;
                    iE =0;
                    continue;
                }

                if( nE > iE){
                    edges[iE] = updateEdge(line);
                    iE++;
                    continue;
                }

                if(line.contains("KABLE")){
                    cables = new Cable[takeNumOf(line)];
                    nC = cables.length;
                    iC = 0;
                    continue;
                }

                if( nC > iC){
                    cables[iC] = updateCable(line);
                    iC++;
                    continue;
                }
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public void useAlgorithm() {

        for (Vertex v : vertices) {
            if (v.getCustomerNum() == -1)
                main = v.getId();
            else if (v.getCustomerNum() == 0)
                optional.add(v.getId());
            else
                target.add(v);
        }

        System.out.println("main " + main);
        for(int i : optional)
            System.out.println("optional " + i);
        for(Vertex v : target)
            System.out.println("target " + v.getId());

        //getting net
        for (Vertex v : target) {
                Path path = Algorithm.Dijkstra(main, v.getId(), vertices, edges);
                paths.add(path);

        }

        nett = sumPaths(paths);
        System.out.println("Net cost = " + netCost(nett));


//        System.out.println("edges");
//        for(int i : nett.getEdgesId())
//            System.out.println(i);
//        System.out.println("vertices");
//        for(int i : nett.getVerticesId())
//            System.out.println(i);
//        System.out.println("fibres");
//        for(Path.Pair p : nett.getFibreNumByEdgesId())
//            System.out.println(p.getLeft()+" : "+p.getRight());
    }

    public void showNet(){
        System.out.println("Number of vertices: "+vertices.length);
        System.out.println("Number of edges: "+edges.length);

        for (int i = 0; i < edges.length; ++i) {
            System.out.println("Id: " + edges[i].getId() + " | " + edges[i].getStartVertex() + " -- " +
                    edges[i].getEndVertex() + " == " + edges[i].getValue());
        }

        Graph graph = new SingleGraph("Net") ;
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.addAttribute("ui.antialias");

        for (int i = 0 ; i < vertices.length ; i++){
            Node n = graph.addNode(vertices[i].getId() + "");
            n.addAttribute("ui.label", "" + vertices[i].getId());
            n.addAttribute("x", vertices[i].getX());
            n.addAttribute("y", vertices[i].getY());
        }

        for (int i = 0; i < edges.length; i++){
            org.graphstream.graph.Edge e = graph.addEdge(edges[i].getId() +"", edges[i].getStartVertex() +"", edges[i].getEndVertex() +"");
            e.addAttribute("ui.label",""+ edges[i].getValue());
        }

        Viewer viewer = graph.display();
        viewer.disableAutoLayout();

        Path path = nett;

        if(path != null){
            for (Integer id: path.getVerticesId()){
                Node n = graph.getNode(id + "");
                n.addAttribute("ui.class", "marked");
            }
            for (Integer id: path.getEdgesId()){
                org.graphstream.graph.Edge e = graph.getEdge(id +"");
                e.addAttribute("ui.class", "marked");
            }
       }
    }

    private int takeNumOf(String line){
        String[] parts =line.split(" ");
        return Integer.parseInt(parts[2]);
        }

    private Vertex updateVertex(String line){
        String[] parts =line.split(" ");
        return new Vertex(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }

    private Edge updateEdge(String line){
        String[] parts =line.split(" ");
        int startId =Integer.parseInt(parts[1]),
                endId =Integer.parseInt(parts[2]);
        return new Edge(Integer.parseInt(parts[0]), startId,
                endId, Integer.parseInt(parts[3]), calcValue(startId, endId));
    }

    private Cable updateCable(String line){
        String[] parts =line.split(" ");
        return new Cable(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]));
    }

    private int calcValue(int startId, int endId){
        double dx = vertices[startId-1].getX() - vertices[endId - 1].getX();
        double dy = vertices[startId-1].getY() - vertices[endId-1].getY();
        Double v = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        return v.intValue();
    }

    private Path sumPaths(ArrayList<Path> paths){
        Path pathsSum = new Path();
        for(Path p : paths){

            //gets number of fibres needed by path
            int clientNum = p.getFibresNeeded();

            for(int i = 0; i < p.getVerticesId().size(); i++) {
                if (!pathsSum.getVerticesId().contains(p.getVerticesId().get(i))) {
                     pathsSum.getVerticesId().add(p.getVerticesId().get(i));
                }
            }
            for(int i = 0; i< p.getEdgesId().size(); i++){
                //if there is no such edge
                if (!pathsSum.getEdgesId().contains(p.getEdgesId().get(i))) {
                    pathsSum.getEdgesId().add(p.getEdgesId().get(i));
                    pathsSum.getFibreNumByEdgesId().add(pathsSum.newPair(p.getEdgesId().get(i),clientNum));
                    //if the edge already exists
                }else {
                    pathsSum.addFibresById(p.getEdgesId().get(i),clientNum);
                }
            }
        }
        return pathsSum;
    }

    private int netCost(Path net){
        int sum = 0;
        for(Path.Pair p : net.getFibreNumByEdgesId()){
            Edge edge =  edges[p.getLeft()-1];

            int cost = 0;
            int capacityCount = 0;
            int fibresCount = p.getRight();

            do{
                Cable cable = cableChoice(fibresCount-capacityCount);
                cost += cable.getCost();
                capacityCount += cable.getCapacity();
            }while(fibresCount > capacityCount);

            sum += edge.getValue()*cost + edge.getDigCost();
        }
        return sum;
    }

    private Cable cableChoice(int fiberNum){
        int i = 0;
        while(fiberNum > cables[i].getCapacity() && i < cables.length-1){
           i++;
        }
        return cables[i];
    }
}