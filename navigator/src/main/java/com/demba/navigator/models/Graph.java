package com.demba.navigator.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterGraph;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.SearchProblem;

public class Graph {
    private HipsterGraph<Vertex, Edge> graph;

    private Graph(HipsterGraph<Vertex, Edge> graph) {
        this.graph = graph;
    }

    public List<Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>();
        graph.vertices().forEach(vertices::add);
        return vertices;
    }

    public Vertex getVertexByName(String name) {
        return getVertices()
                .stream()
                .filter(Vertex::hasName)
                .filter(vertex -> vertex.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Set<String> getVerticesNames() {
        return getVertices()
                .stream()
                .filter(Vertex::hasName)
                .map(Vertex::getName)
                .collect(Collectors.toSet());
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        graph.edges().forEach(edge -> edges.add(edge.getEdgeValue()));
        return edges;
    }

    public static Graph from(Path path) {
        List<Vertex> vertices = path.getPoints();

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = 0; j < vertices.size(); j++) {
                if (vertices.get(i) != vertices.get(j) && areVerticesClose(vertices.get(j),vertices.get(i))) {
                    double newLat = (Double.parseDouble(vertices.get(i).getLatitude()) + Double.parseDouble(vertices.get(j).getLatitude())) / 2;
                    double newLon = (Double.parseDouble(vertices.get(i).getLongitude()) + Double.parseDouble(vertices.get(j).getLongitude())) / 2;

                    Vertex newVertex = new Vertex(String.valueOf(newLat), String.valueOf(newLon), vertices.get(i).getFloor());

                    vertices.set(i, newVertex);
                    vertices.set(j, newVertex);
                }
            }
        }

        GraphBuilder<Vertex, Edge> graphBuilder = GraphBuilder.create();

        for (int i = 0; i < vertices.size() - 1; i++) {
            Edge edge = new Edge(vertices.get(i), vertices.get(i + 1));
            graphBuilder
                    .connect(edge.getSource())
                    .to(edge.getDestination())
                    .withEdge(edge);
        }

        return new Graph(graphBuilder.createUndirectedGraph());
    }

    private static boolean areVerticesClose(Vertex vertex1, Vertex vertex2) {
        return vertex1.getDistance(vertex2) < 10;
    }

    public static Graph from(List<Edge> edges) {
        GraphBuilder<Vertex, Edge> graphBuilder = GraphBuilder.create();

        for (Edge edge : edges) {
            graphBuilder
                    .connect(edge.getSource())
                    .to(edge.getDestination())
                    .withEdge(edge);
        }

        return new Graph(graphBuilder.createUndirectedGraph());
    }

    public Path getShortestPath(Vertex source, Vertex destination) {
        SearchProblem<Edge, Vertex, WeightedNode<Edge, Vertex, Double>> searchProblem = GraphSearchProblem
                .startingFrom(source)
                .in(graph)
                .takeCostsFromEdges()
                .build();

        List<Vertex> result = Hipster
                .createDijkstra(searchProblem)
                .search(destination)
                .getOptimalPaths()
                .get(0);

        return new Path(result);
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
