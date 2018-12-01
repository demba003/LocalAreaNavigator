package com.demba.navigator.models;

import com.demba.navigator.SampleGpxTrack;
import com.demba.navigator.entities.Gpx;
import com.demba.navigator.entities.gpx.GpxEntity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void fromTest() throws Exception {
        // given
        GpxEntity gpxEntity = Gpx.read(SampleGpxTrack.track);

        // when
        Path path = Path.from(gpxEntity);

        // then
        assertEquals("50.07247282", path.getPoints().get(0).getLatitude());
        assertEquals("19.94061201", path.getPoints().get(0).getLongitude());

        assertEquals("50.07181176", path.getPoints().get(1).getLatitude());
        assertEquals("19.94025796", path.getPoints().get(1).getLongitude());

        assertEquals("50.07148123", path.getPoints().get(2).getLatitude());
        assertEquals("19.94181364", path.getPoints().get(2).getLongitude());
    }

    @Test
    public void simplifyTest() {
        // given
        Vertex v1 = new Vertex("50.07247","19.94061", "0");
        Vertex v2 = new Vertex("50.07248","19.94061", "0");
        Vertex v3 = new Vertex("50.07268","19.94061", "0");

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        Path path = new Path(vertices);

        // when
        path.simplify();

        // then
        assertSame(path.getPoints().get(0), path.getPoints().get(1));
    }

    @Test
    public void maxFloorTest() {
        // given
        Vertex v1 = new Vertex("50", "19", "0");
        Vertex v2 = new Vertex("51", "19", "1");
        Vertex v3 = new Vertex("51", "19", "2");

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        Path path = new Path(vertices);

        // when
        int maxFloor = path.getMaxFloor();

        // then
        assertEquals(2, maxFloor);
    }

    @Test
    public void minFloorTest() {
        // given
        Vertex v1 = new Vertex("50", "19", "0");
        Vertex v2 = new Vertex("51", "19", "1");
        Vertex v3 = new Vertex("51", "19", "2");

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        Path path = new Path(vertices);

        // when
        int maxFloor = path.getMinFloor();

        // then
        assertEquals(0, maxFloor);
    }

    @Test
    public void oneFloorSubPathTest() {
        // given
        Vertex v1 = new Vertex("50", "19", "0");
        Vertex v2 = new Vertex("51", "19", "0");
        Vertex v3 = new Vertex("51", "19", "1");

        List<Vertex> vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        List<Vertex> vertices2 = new ArrayList<>();
        vertices2.add(v1);
        vertices2.add(v2);

        Path path = new Path(vertices);

        // when
        Path subPath = path.oneFloorSubPath("0");

        // then
        assertEquals(2, subPath.getPoints().size());
        assertEquals(vertices2, subPath.getPoints());
    }

}