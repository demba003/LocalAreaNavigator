package com.demba.navigator.models;

import com.demba.navigator.entities.gpx.TrkptEntity;

import org.junit.Test;

import static org.junit.Assert.*;

public class VertexTest {

    @Test
    public void basicTest() {
        // given
        Vertex vertex = new Vertex("50", "19", "0", "Name");

        // when
        vertex.setFloor("1");
        vertex.setName("Other");

        // then
        assertEquals("1", vertex.getFloor());
        assertEquals("Other", vertex.getName());
        assertEquals("(Other)[50, 19 : 1]", vertex.toString());
    }

    @Test
    public void fromTest() {
        // given
        TrkptEntity trkptEntity = new TrkptEntity();
        trkptEntity.lat = "50.07247282";
        trkptEntity.lon = "19.94061201";

        // when
        Vertex vertex = Vertex.from(trkptEntity);
        vertex.setName("name");

        // then
        assertEquals("50.07247282", vertex.getLatitude());
        assertEquals("19.94061201", vertex.getLongitude());
        assertEquals("0", vertex.getFloor());
        assertEquals("name", vertex.getName());
        assertTrue(vertex.hasName());
    }

    @Test
    public void getDistanceTest() {
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
        double distance = vertex1.getDistance(vertex2);

        // then
        assertEquals(78, distance, 0.5);
    }
}