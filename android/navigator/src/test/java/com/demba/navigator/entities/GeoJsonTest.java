package com.demba.navigator.entities;

import com.demba.navigator.SampleGeojsonTrack;
import com.demba.navigator.models.Edge;
import com.demba.navigator.models.Graph;
import com.demba.navigator.models.Path;
import com.demba.navigator.models.Vertex;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GeoJsonTest {

    @Test
    public void encodeGraphTest() {
        // given
        Vertex v1 = new Vertex("50", "19", "0");
        Vertex v2 = new Vertex("51", "19", "0");
        Vertex v3 = new Vertex("50", "20", "0");

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);

        List<Edge> edges = new ArrayList<>();
        edges.add(e1);
        edges.add(e2);

        Graph graph = Graph.from(vertices, edges);

        // when
        String data = GeoJson.encode(graph);
        Graph parsedGraph = GeoJson.parse(data);

        // then
        assertEquals(graph.getEdges(), parsedGraph.getEdges());
        assertEquals(graph.getVertices(), parsedGraph.getVertices());
    }

    @Test
    public void parseGraphTest() {
        // given
        String data = SampleGeojsonTrack.track;

        // when
        Graph graph = GeoJson.parse(data);

        // then
        assertEquals(2 , graph.getEdges().size());
        assertEquals(3, graph.getVertices().size());
    }

    @Test
    public void encodeTest() {
        // given
        Vertex v1 = new Vertex("50", "19", "0");
        Vertex v2 = new Vertex("51", "19", "0");

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);

        Path path = new Path(vertices);

        // when
        String json = GeoJson.encode(path);
        Graph graph = GeoJson.parse(json);

        //then
        assertEquals(vertices.size(), graph.getVertices().size());
    }
}