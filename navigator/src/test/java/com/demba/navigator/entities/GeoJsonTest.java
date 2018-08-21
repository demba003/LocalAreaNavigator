package com.demba.navigator.entities;

import com.demba.navigator.SampleGeojsonTrack;
import com.demba.navigator.SampleGpxTrack;
import com.demba.navigator.models.Graph;
import com.demba.navigator.models.Path;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeoJsonTest {

    /*@Test
    public void encodeGraphTest() throws Exception {
        // given
        Path path = Path.from(Gpx.read(SampleGpxTrack.track));
        Graph graph = Graph.from(path);

        // when
        String data = GeoJson.encode(graph);
        Graph parsedGraph = GeoJson.parse(data);

        // then
        assertEquals(graph.getVertices().size(), parsedGraph.getVertices().size());
        assertEquals(graph.getEdges().size(), parsedGraph.getEdges().size());
    }

    @Test
    public void parseGraphTest() {
        // given
        String data = SampleGeojsonTrack.track;

        // when
        Graph graph = GeoJson.parse(data);

        // then
        assertEquals(2 * 2, graph.getEdges().size());
        assertEquals(3, graph.getVertices().size());
    }*/
}