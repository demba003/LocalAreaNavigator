package com.demba.navigator.models;

import com.demba.navigator.SampleGeojsonTrack;
import com.demba.navigator.SampleGpxTrack;
import com.demba.navigator.entities.GeoJson;
import com.demba.navigator.entities.Gpx;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GraphTest {

    @Test
    public void fromTest() throws Exception {
        // given
        Path path = Path.from(Gpx.read(SampleGpxTrack.track));

        // when
        Graph graph = Graph.from(path);

        // then
        assertEquals(2 * 2, graph.getEdges().size());
        assertEquals(3, graph.getVertices().size());
    }

    @Test
    public void getShortestPathTest() throws Exception {
        // given
        Path path = Path.from(Gpx.read(SampleGpxTrack.track));
        Graph graph = Graph.from(path);

        // when
        Path shortestPath = graph.getShortestPath(path.getPoints().get(0), path.getPoints().get(path.getPoints().size()-1));

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
        Set<String> names = graph.getVerticesNames();

        // then
        assertEquals(1, names.size());
        assertEquals("0", names.stream().findFirst().orElse(""));
    }
}