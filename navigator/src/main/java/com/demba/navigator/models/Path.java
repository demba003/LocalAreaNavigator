package com.demba.navigator.models;

import com.demba.navigator.entities.gpx.GpxEntity;

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
