package com.demba.navigator.models;

import com.demba.navigator.entities.gpx.TrkptEntity;

import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void edgeTest() {
        // given
        TrkptEntity trkptEntity = new TrkptEntity();
        trkptEntity.lat = "50.07247282";
        trkptEntity.lon = "19.94061201";

        TrkptEntity trkptEntity2 = new TrkptEntity();
        trkptEntity2.lat = "50.07181176";
        trkptEntity2.lon = "19.94025796";

        Vertex vertex1 = Vertex.from(trkptEntity);
        Vertex vertex2 = Vertex.from(trkptEntity2);

        // when
        Edge edge = new Edge(vertex1, vertex2);

        // then
        assertEquals(vertex1, edge.getSource());
        assertEquals(vertex2, edge.getDestination());
        assertEquals(78, edge.getDistance(), 0.5);
    }
}