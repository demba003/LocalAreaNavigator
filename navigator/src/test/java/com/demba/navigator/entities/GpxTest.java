package com.demba.navigator.entities;

import com.demba.navigator.SampleGpxTrack;
import com.demba.navigator.entities.gpx.GpxEntity;

import org.junit.Test;

import static org.junit.Assert.*;

public class GpxTest {

    @Test
    public void readTest() throws Exception {
        // given
        String data = SampleGpxTrack.track;

        // when
        GpxEntity gpxEntity = Gpx.read(data);

        // then
        assertEquals("50.07247282", gpxEntity.track.segment.points.get(0).lat);
        assertEquals("19.94061201", gpxEntity.track.segment.points.get(0).lon);

        assertEquals("50.07181176", gpxEntity.track.segment.points.get(1).lat);
        assertEquals("19.94025796", gpxEntity.track.segment.points.get(1).lon);

        assertEquals("50.07148123", gpxEntity.track.segment.points.get(2).lat);
        assertEquals("19.94181364", gpxEntity.track.segment.points.get(2).lon);
    }
}