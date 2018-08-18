package com.demba.navigator.entities.gpx;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "trkseg", strict = false)
public class TrksegEntity {

    @ElementList(name = "trkpt", inline = true, type = TrkptEntity.class, required = false)
    public List<TrkptEntity> points;
}
