package com.demba.navigator.models;

import com.demba.navigator.SampleGeojsonTrack;
import com.demba.navigator.SampleGpxTrack;
import com.demba.navigator.entities.GeoJson;
import com.demba.navigator.entities.Gpx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void fromTest() {
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

        // when
        Graph graph = Graph.from(vertices, edges);

        // then
        assertEquals(new HashSet<>(vertices), graph.getVertices());
        assertEquals(new HashSet<>(edges), graph.getEdges());

    }

    @Test
    public void getShortestPathTest() throws Exception {
        // given
        Path path = Path.from(Gpx.read(SampleGpxTrack.track));
        Graph graph = GeoJson.parse(GeoJson.encode(path));

        // when
        Path shortestPath = graph.getShortestPath(
                path.getPoints().get(0), path.getPoints().get(path.getPoints().size()-1));

        // then
        assertEquals(path, shortestPath);
    }

    @Test
    public void getVertexByNameTest() {
        // given
        Graph graph = GeoJson.parse(SampleGeojsonTrack.track);

        // when
        Vertex vertex = graph.getVertexByName("0");

        // then
        assertEquals("0", vertex.getName());
        assertEquals("19.94061201", vertex.getLongitude());
        assertEquals("50.07247282", vertex.getLatitude());
    }

    @Test
    public void getVerticesNamesTest() {
        // given
        Graph graph = GeoJson.parse(SampleGeojsonTrack.track);

        // when
        List<String> names = graph.getVerticesNames();

        // then
        assertEquals(1, names.size());
        assertEquals("0", names.stream().findFirst().orElse(""));
    }

    @Test
    public void getNearestVertexTest() {
        // given
        Graph graph = GeoJson.parse(SampleGeojsonTrack.track);

        // when
        Vertex vertex = graph.getNearestVertex("50.07247", "19.9406");

        // then
        assertEquals("50.07247282", vertex.getLatitude());
        assertEquals("19.94061201", vertex.getLongitude());
    }
}