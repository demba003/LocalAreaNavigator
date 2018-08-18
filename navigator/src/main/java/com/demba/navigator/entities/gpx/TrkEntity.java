package com.demba.navigator.entities.gpx;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "trk", strict = false)
public class TrkEntity {

    @Element(type = TrksegEntity.class, name = "trkseg")
    public TrksegEntity segment;
}
