package com.demba.navigator.models;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(getSource(), edge.getSource()) &&
                Objects.equals(getDestination(), edge.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getDestination());
    }

    @Override
    public String toString() {
        return "[" + source + " - " + destination + "]";
    }
}
