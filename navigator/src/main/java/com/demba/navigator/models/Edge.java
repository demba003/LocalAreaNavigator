package com.demba.navigator.models;

public class Edge {
    private final Vertex source;
    private final Vertex destination;
    private final double distance;

    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
        this.distance = source.getDistance(destination);
    }

    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "[" + source + " - " + destination + "]";
    }


}
