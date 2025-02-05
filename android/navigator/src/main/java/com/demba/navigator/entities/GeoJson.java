package com.demba.navigator.entities;

import com.demba.navigator.models.Edge;
import com.demba.navigator.models.Graph;
import com.demba.navigator.models.Path;
import com.demba.navigator.models.Vertex;

import org.json2.JSONException;
import org.json2.JSONObject;
import org.json2.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GeoJson {
    public static String encode(Graph graph) {
        JSONStringer json = new JSONStringer();

        json.object()
                .key("type").value("FeatureCollection")
                .key("features").array();

        encodeVertices(json, new ArrayList<>(graph.getVertices()));
        encodeEdges(json, new ArrayList<>(graph.getEdges()));

        json.endArray().endObject();

        return json.toString();
    }

    public static Graph parse(String geojson) {
        JSONObject jsonObject = new JSONObject(geojson);
        List<Edge> edges = new ArrayList<>();
        Map<Integer, Vertex> vertices = new HashMap<>();

        for (Object object : jsonObject.getJSONArray("features")) {
            JSONObject json = (JSONObject) object;

            if(json.getJSONObject("geometry").get("type").toString().equals("Point")) {
                String lat = json.getJSONObject("geometry").getJSONArray("coordinates").get(1).toString();
                String lon = json.getJSONObject("geometry").getJSONArray("coordinates").get(0).toString();
                String name = json.getJSONObject("properties").get("name").toString();
                String floor = json.getJSONObject("properties").get("floor").toString();

                Vertex vertex = new Vertex(lat, lon, floor, name.equals("null") ? null : name);
                vertices.put(vertex.hashCode(), vertex);
            }


            if (json.getJSONObject("geometry").get("type").toString().equals("LineString")) {
                String lat1 = json.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0).get(1).toString();
                String lon1 = json.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0).get(0).toString();

                String lat2 = json.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1).toString();
                String lon2 = json.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0).toString();

                Vertex vertex1 = vertices.get(new Vertex(lat1, lon1, null).hashCode());
                Vertex vertex2 = vertices.get(new Vertex(lat2, lon2, null).hashCode());

                edges.add(new Edge(vertex1, vertex2));
            }
        }

        return Graph.from(vertices.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()), edges);
    }

    public static String encode(Path path) {
        JSONStringer json = new JSONStringer();

        json.object()
                .key("type").value("FeatureCollection")
                .key("features").array();

        encodeVertices(json, path.getPoints());
        List<Edge> edges = makeEdgesFromVertices(path.getPoints());
        encodeEdges(json, edges);

        json.endArray().endObject();

        return json.toString();
    }

    private static void encodeVertices(JSONStringer json, List<Vertex> vertices) throws JSONException {
        Set<Vertex> usedVertices = new HashSet<>();
        for (Vertex vertex : vertices) {
            if (!usedVertices.contains(vertex)) {
                json
                    .object()
                        .key("type").value("Feature")
                        .key("properties")
                            .object()
                                .key("name").value(vertex.getName())
                                .key("floor").value(vertex.getFloor())
                            .endObject()
                        .key("geometry")
                            .object()
                                .key("type").value("Point")
                                .key("coordinates")
                                    .array()
                                        .value(Double.parseDouble(vertex.getLongitude()))
                                        .value(Double.parseDouble(vertex.getLatitude()))
                                    .endArray()
                            .endObject()
                    .endObject();
                usedVertices.add(vertex);
            }
        }
    }

    private static void encodeEdges(JSONStringer json, List<Edge> edges) throws JSONException {
        for(Edge edge : edges) {
            json
                .object()
                    .key("type").value("Feature")
                    .key("properties").object().endObject()
                    .key("geometry")
                        .object()
                            .key("type").value("LineString")
                            .key("coordinates")
                                .array()
                                    .array()
                                        .value(Double.parseDouble(edge.getSource().getLongitude()))
                                        .value(Double.parseDouble(edge.getSource().getLatitude()))
                                    .endArray()
                                    .array()
                                        .value(Double.parseDouble(edge.getDestination().getLongitude()))
                                        .value(Double.parseDouble(edge.getDestination().getLatitude()))
                                    .endArray()
                                .endArray()
                        .endObject()
                .endObject();
        }
    }

    private static List<Edge> makeEdgesFromVertices(List<Vertex> vertices) {
        List<Edge> list = new ArrayList<>();
        for (int i = 0; i < vertices.size() - 1; i++) {
            if (!vertices.get(i).equals(vertices.get(i + 1))) {
                Edge edge = new Edge(vertices.get(i), vertices.get(i + 1));
                if (!list.contains(edge)) {
                    list.add(edge);
                }
            }
        }
        return list;
    }
}
