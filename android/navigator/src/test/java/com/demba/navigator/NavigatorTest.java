package com.demba.navigator;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NavigatorTest {

    @Test
    public void getWaypointsNamesTest() throws Exception {
        // given
        Navigator navigator = Navigator.fromGeojson(
                Navigator.getGeoJsonFromGpx(SampleGpxTrack.track));

        // when
        List<String> names = navigator.getWaypointsNames();

        //then
        assertEquals(0, names.size());
    }

    @Test
    public void getShortestPathTest() throws Exception {
        // given
        Navigator navigator = Navigator.fromGeojson(
                Navigator.getGeoJsonFromGpx(SampleGpxTrack.track));

        // when
        List<String> names = navigator.getWaypointsNames();

        //then
        assertEquals(0, names.size());
    }
}
