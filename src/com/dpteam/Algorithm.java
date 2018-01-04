package com.dpteam;

public class Algorithm {

    static Path Dijkstra(int startV, int endV, Vertex vertices[], Edge edges[]){
        int V = vertices.length;
        int edgeId ;
        // The output array. dist[i] will hold the shortest distance from src to i
        int dist[] = new int[V];

        // sptSet[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        Boolean sptSet[] = new Boolean[V];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < V; i++)
        {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[startV - 1] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < V-1; count++)
        {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = minDistance(dist, sptSet, V);

            // Mark the picked vertex as processed
            sptSet[u] = true;


            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < V; v++) {

                edgeId = getEdge(u + 1, v + 1, edges);

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!sptSet[v] && edgeId != 0 &&
                        dist[u] != Integer.MAX_VALUE &&
                        dist[u] + edges[edgeId -1].getValue() < dist[v])
                    dist[v] = dist[u] + edges[edgeId -1].getValue();
            }
        }

//        Path path = null;
//        if (optional != null) {
//            // Getting Stainer tree
//            for (Vertex sV : vertices) {
//                endV = sV.getId();
//                if (endV == startV || optional.contains(endV))
//                    continue;
//                Path shortestPath = new Path();
//                int target = endV;
//                int id = 0, ver = 0;
//                shortestPath.vertexesId.add(target);
//                while (target != startV) {
//                    for (int v = 0; v < V; v++) {
//
//                        edgeId = getEdge(v + 1, target, edges);
//                        if (sptSet[v] && edgeId != 0 && dist[target - 1] > dist[v]) {
//                            if (ver == 0 || dist[v] < dist[ver - 1]) {
//                                ver = v + 1;
//                                id = edgeId;
//                            }
//                        }
//                    }
//                    shortestPath.edgesId.add(id);
//                    target = ver;
//                    shortestPath.vertexesId.add(target);
//                }
//                if (path == null) {
//                    path = shortestPath;
//                } else {
//                    for (int idS : shortestPath.edgesId) {
//                        if (!path.edgesId.contains(idS))
//                            path.edgesId.add(idS);
//                    }
//                    for (int idS : shortestPath.vertexesId) {
//                        if (!path.vertexesId.contains(idS))
//                            path.vertexesId.add(idS);
//                    }
//                }
//            }
//        }
//        else {
            // Getting shortest path
            Path shortestPath = new Path();
            shortestPath.setFibresNeeded(vertices[endV-1].getCustomerNum());
            int target = endV;
            int id = 0, vert = 0;
            shortestPath.getVerticesId().add(target);
            while (target != startV) {
                for (int v = 0; v < V; v++) {
                    edgeId = getEdge(v + 1, target, edges);
                    if (sptSet[v] && edgeId != 0 && dist[target -1] > dist[v]){
                        if(vert == 0 || dist[v] < dist[vert-1]) {
                            vert = v + 1;
                            id = edgeId;
                        }
                    }
                }
                shortestPath.getEdgesId().add(id);
                target = vert;
                shortestPath.getVerticesId().add(target);
            }
       // }

        return shortestPath;
    }

    private static int minDistance(int dist[], Boolean sptSet[], int V)
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index=-1;

        for (int v = 0; v < V; v++)
            if (!sptSet[v] && dist[v] <= min){
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

    public static int getEdge(int Id, int Id2, Edge edges[]){
        for(Edge edge : edges){
            if(edge.getStartVertex() == Id &&
                    edge.getEndVertex() == Id2
                    || (edge.getStartVertex() == Id2 &&
                    edge.getEndVertex() == Id))
                return edge.getId();
        }
        return 0;
    }
}

