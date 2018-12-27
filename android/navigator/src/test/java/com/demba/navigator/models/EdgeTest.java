package com.demba.navigator.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void edgeTest() {
        // given
        Vertex vertex1 = new Vertex("50.07247282", "19.94061201", "0");
        Vertex vertex2 = new Vertex("50.07181176", "19.94025796", "0");

        // when
        Edge edge = new Edge(vertex1, vertex2);

        // then
        assertEquals(vertex1, edge.getSource());
        assertEquals(vertex2, edge.getDestination());
        assertEquals(78, edge.getDistance(), 0.5);
        assertEquals("[[50.07247282, 19.94061201 : 0] - [50.07181176, 19.94025796 : 0]]", edge.toString());
    }
}