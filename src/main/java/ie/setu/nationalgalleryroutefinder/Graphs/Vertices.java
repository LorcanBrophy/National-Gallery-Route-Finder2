package ie.setu.nationalgalleryroutefinder.Graphs;

import ie.setu.nationalgalleryroutefinder.model.MyArrayList;

public class Vertices<T> {

    private final T data;
    private final MyArrayList<Edge<T>> edges;

    public Vertices(T data) {
        this.data = data;
        this.edges = new MyArrayList<>();
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