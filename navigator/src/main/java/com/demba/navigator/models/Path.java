package com.demba.navigator.models;

import com.demba.navigator.entities.gpx.GpxEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Path {
    private final List<Vertex> points;

    public static Path from(GpxEntity gpxEntity) {
        return new Path(
                gpxEntity.track.segment.points
                        .stream()
                        .map(Vertex::from)
                        .collect(Collectors.toList()));
    }

    Path(List<Vertex> points) {
        this.points = points;
    }


    public Path simplify() {
        for (int i = 0; i < getPoints().size(); i++) {
            for (int j = 0; j < getPoints().size(); j++) {
                if (getPoints().get(i) != getPoints().get(j) && areVerticesClose(getPoints().get(j),getPoints().get(i))) {
                    double newLat = round((Double.parseDouble(getPoints().get(i).getLatitude()) + Double.parseDouble(getPoints().get(j).getLatitude())) / 2, 5);
                    double newLon = round((Double.parseDouble(getPoints().get(i).getLongitude()) + Double.parseDouble(getPoints().get(j).getLongitude())) / 2, 5);

                    Vertex newVertex = new Vertex(String.valueOf(newLat), String.valueOf(newLon), getPoints().get(i).getFloor(), getPoints().get(i).getName());

                    getPoints().set(i, newVertex);
                    getPoints().set(j, newVertex);
                }
            }
        }

        return this;
    }

    private static boolean areVerticesClose(Vertex vertex1, Vertex vertex2) {
        return vertex1.getDistance(vertex2) < 15;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public List<Vertex> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Path{" + "points=" + points + '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Path path = (Path) object;
        return Objects.equals(getPoints(), path.getPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPoints());
    }
}
