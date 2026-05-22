package com.nationalgalleryroutefinder.graph;

import com.nationalgalleryroutefinder.model.MyArrayList;
import com.nationalgalleryroutefinder.model.MyHashtable;

public class Graph<T> {

    private final MyHashtable<Integer, Vertices<T>> vertex;
    //a
    public Graph() {
        this.vertex = new MyHashtable<>();
    }

    public void addVertex(int key, T data) {
        vertex.put(key, new Vertices<>(data));
    }

    public Vertices<T> getVertex(int id) {
        return vertex.get(id);
    }

    public MyArrayList<Vertices<T>> getAllVertices() {
        MyArrayList<Vertices<T>> list = new MyArrayList<>();
        for (MyHashtable.Entry<Integer, Vertices<T>> entry : vertex) {
            list.add(entry.getValue());
        }
        return list;
    }

    public void addDirectedEdge(int from, int to, double weight) {
        Vertices<T> source = vertex.get(from);
        Vertices<T> terminus = vertex.get(to);

        if (source == null || terminus == null) {
            throw new IllegalArgumentException("Both vertices must exist");
        }

        source.connectDirected(terminus, weight);
    }

    public void addUndirectedEdge(int from, int to, double weight) {
        Vertices<T> source = vertex.get(from);
        Vertices<T> terminus = vertex.get(to);

        if (source == null || terminus == null) {
            throw new IllegalArgumentException("Both vertices must exist");
        }

        source.connectUndirected(terminus, weight);
    }
}