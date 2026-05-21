package com.nationalgalleryroutefinder.graph;

public class Edge<T> {

    public Vertices<T> destination;
    public double weight;

    public Edge(Vertices<T> destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Vertices<T> getDestination() {
        return destination;
    }
    public double getWeight() {
        return weight;
    }
}