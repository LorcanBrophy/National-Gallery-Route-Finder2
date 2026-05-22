package com.nationalgalleryroutefinder.graph;

import com.nationalgalleryroutefinder.model.MyArrayList;

public class Vertices<T> {

    private final int id;
    private final T data;
    private final MyArrayList<Edge<T>> edges;

    public Vertices(int id, T data) {
        this.id = id;
        this.data = data;
        this.edges = new MyArrayList<>();
    }

    public int getId() {
        return id;
    }

    public T getData() {
        return data;
    }

    public MyArrayList<Edge<T>> getEdges() {
        return edges;
    }

    public void connectUndirected(Vertices<T> destNode, double weight) {
        edges.add(new Edge<>(destNode, weight));
        destNode.edges.add(new Edge<>(this, weight));
    }

    public void connectDirected(Vertices<T> destNode, double weight) {
        edges.add(new Edge<>(destNode, weight));
    }
}