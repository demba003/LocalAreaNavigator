package com.demba.navigator;

import com.demba.navigator.entities.GeoJson;
import com.demba.navigator.entities.Gpx;
import com.demba.navigator.models.Graph;
import com.demba.navigator.models.Path;

import java.util.List;

public class Navigator {
    private Graph graph;

    private Navigator(Graph graph) {
        this.graph = graph;
    }

    public static Navigator fromGeojson(String geoJsonData) {
        return new Navigator(GeoJson.parse(geoJsonData));
    }

    public String getShortestPathGeoJson(String source, String destination) {
        Path shortestPath = graph
                .getShortestPath(
                        graph.getVertexByName(source),
                        graph.getVertexByName(destination)
                );
        return GeoJson.encode(shortestPath);
    }

    public String getShortestPathGeoJson(String sourceLatitude, String sourceLongitude, String destination) {
        Path shortestPath = graph
                .getShortestPath(
                        graph.getNearestVertex(sourceLatitude, sourceLongitude),
                        graph.getVertexByName(destination)
                );
        return GeoJson.encode(shortestPath);
    }

    public List<String> getWaypointsNames() {
        return graph.getVerticesNames();
    }

    public static String getGeoJsonFromGpx(String gpxData) throws Exception {
        return GeoJson.encode(Path.from(Gpx.read(gpxData)).simplify());
    }
}
