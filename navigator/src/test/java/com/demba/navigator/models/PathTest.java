package com.demba.navigator.models;

import com.demba.navigator.SampleGpxTrack;
import com.demba.navigator.entities.Gpx;
import com.demba.navigator.entities.gpx.GpxEntity;

import org.junit.Test;

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
}