package com.demba.navigator.models;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Objects;

public class Edge extends DefaultWeightedEdge {
    private Vertex source;
    private Vertex destination;
    private double distance;

    public Edge() {
        super();
    }

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
