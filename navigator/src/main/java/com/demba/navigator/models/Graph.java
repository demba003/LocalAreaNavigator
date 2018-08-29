package com.demba.navigator.models;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph {
    private org.jgrapht.Graph<Vertex, DefaultWeightedEdge> graph;
    private Set<Edge> edges;

    private Graph(org.jgrapht.Graph<Vertex, DefaultWeightedEdge> graph, List<Edge> edges) {
        this.graph = graph;
        this.edges = new HashSet<>(edges);
    }

    public Set<Vertex> getVertices() {
        return graph.vertexSet();
    }

    public Vertex getVertexByName(String name) {
        return getVertices()
                .stream()
                .filter(Vertex::hasName)
                .filter(vertex -> vertex.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<String> getVerticesNames() {
        return getVertices()
                .stream()
                .filter(Vertex::hasName)
                .map(Vertex::getName)
                .collect(Collectors.toList());
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public static Graph from(List<Vertex> vertices, List<Edge> edges) {
        org.jgrapht.Graph<Vertex, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        vertices.forEach(graph::addVertex);
        for (Edge edge : edges) {
            graph.addEdge(edge.getSource(), edge.getDestination());
        }

        return new Graph(graph, edges);
    }

    public Path getShortestPath(Vertex source, Vertex destination) {
        DijkstraShortestPath<Vertex, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(this.graph);
        return new Path(dijkstra
                .getPath(source, destination)
                .getVertexList());
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && this.graph.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.graph);
    }
}
