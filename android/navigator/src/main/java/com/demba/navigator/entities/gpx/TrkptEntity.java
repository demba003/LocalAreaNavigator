package com.demba.navigator.entities.gpx;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "trkpt", strict = false)
public class TrkptEntity {

    @Attribute
    public String lat;

    @Attribute
    public String lon;
}
