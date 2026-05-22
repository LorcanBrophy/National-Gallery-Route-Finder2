package com.nationalgalleryroutefinder.algos;

import com.nationalgalleryroutefinder.graph.Edge;
import com.nationalgalleryroutefinder.graph.Graph;
import com.nationalgalleryroutefinder.graph.Vertices;
import com.nationalgalleryroutefinder.model.MyArrayList;
import com.nationalgalleryroutefinder.model.MyHashtable;

import java.util.List;

public final class BFS {
    public static <T> List<T> traverse(Graph<T> graph, int startID, int endID) {
        // final correct path to be returned
        List<T> correctPath = new MyArrayList<>();

        Vertices<T> start = graph.getVertex(startID);
        Vertices<T> end = graph.getVertex(endID);

        if (start == null || end == null) return correctPath;

        // tracks visited vertices using room/vertex IDs
        MyHashtable<Integer, Vertices<T>> visited = new MyHashtable<>();
        visited.put(startID, start);

        // add to back, remove from front to maintain FIFO order
        MyArrayList<Vertices<T>> queue = new MyArrayList<>();
        queue.add(start);

        // key = child vertex ID, value = parent vertex
        MyHashtable<Integer, Vertices<T>> parent = new MyHashtable<>();
        parent.put(startID, null);

        boolean found = false;

        while (!queue.isEmpty()) {
            Vertices<T> current = queue.remove(0);

            if (current.getId() == endID) {
                found = true;
                break;
            }

            // iterate through neighbours of current vertex
            for (Edge<T> edge : current.getEdges()) {
                Vertices<T> neighbour = edge.getDestination();
                int neighbourID = neighbour.getId();

                // only visit unvisited neighbours
                if (!visited.containsKey(neighbourID)) {
                    visited.put(neighbourID, neighbour);
                    queue.add(neighbour);

                    // record that current is the parent of this neighbour
                    parent.put(neighbourID, current);
                }
            }
        }

        if (!found) return correctPath;

        // reconstruct path by walking back through parent map from end to start
        MyArrayList<Vertices<T>> reversePath = new MyArrayList<>();
        Vertices<T> current = end;

        while (current != null) {
            reversePath.add(current);
            current = parent.get(current.getId());
        }

        // reversePath, reverse it into correctPath
        for (int i = reversePath.size() - 1; i >= 0; i--) {
            correctPath.add(reversePath.get(i).getData());
        }

        return correctPath;
    }
}