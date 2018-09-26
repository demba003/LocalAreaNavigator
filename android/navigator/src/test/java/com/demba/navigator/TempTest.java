package com.demba.navigator;

import com.demba.navigator.entities.GeoJson;
import com.demba.navigator.entities.Gpx;
import com.demba.navigator.models.Graph;
import com.demba.navigator.models.Path;

import org.junit.Test;

public class TempTest {

    @Test
    public void kampus() throws Exception {
        //System.out.println(GeoJson.encode(Graph.from(Path.from(Gpx.read(SampleGpxTrack.track2)))));
        //System.out.println(GeoJson.encode(Path.from(Gpx.read(SampleGpxTrack.track3))));
        //System.out.println(GeoJson.encode(GeoJson.parse(GeoJson.encode(Path.from(Gpx.read(SampleGpxTrack.track3)).simplify()))));


        String data = Navigator.getGeoJsonFromGpx(SampleGpxTrack.track2);

        System.out.println(data);

        //Graph graph = GeoJson.parse(data);

        //Navigator navigator = Navigator.fromGeojson(data);

        //System.out.println(navigator.getShortestPathGeoJson("2", "0"));
    }

}
