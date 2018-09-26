package com.demba.navigator.models;

import com.demba.navigator.entities.gpx.TrkptEntity;

import java.util.Objects;

public class Vertex {

    private final String latitude;
    private final String longitude;
    private String floor;
    private String name;

    public Vertex(String latitude, String longitude, String floor, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.name = name;
    }

    public Vertex(String latitude, String longitude, String floor) {
        this(latitude, longitude, floor, null);
    }

    public static Vertex from(TrkptEntity trkptEntity) {
        return new Vertex(
                trkptEntity.lat,
                trkptEntity.lon,
                "0");
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasName() {
        return name != null;
    }

    public double getDistance(Vertex vertex) {
        return calculateDistanceOnSurface(Double.parseDouble(this.getLatitude()), Double.parseDouble(this.getLongitude()),
                Double.parseDouble(vertex.getLatitude()), Double.parseDouble(vertex.getLongitude()));
    }

    private double calculateDistanceOnSurface(double latitude1, double longitude1, double latitude2, double longitude2) {
        double earth_radius = 6371;
        double deltaLat = Math.toRadians(latitude2 - latitude1);
        double deltaLon = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return earth_radius * c * 1000; //return in meters
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(latitude + longitude);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(getLatitude(), vertex.getLatitude()) &&
                Objects.equals(getLongitude(), vertex.getLongitude());
    }

    @Override
    public String toString() {
        if (name == null) {
            return "[" + latitude + ", " + longitude + " : " + floor + "]";
        } else {
            return "(" + name + ")[" + latitude + ", " + longitude + " : " + floor + "]";
        }

    }
}
