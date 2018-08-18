package com.demba.navigator.entities;

import com.demba.navigator.entities.gpx.GpxEntity;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Gpx {
    public static GpxEntity read(String gpxData) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(GpxEntity.class, gpxData);
    }
}
