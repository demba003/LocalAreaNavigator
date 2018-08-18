package com.demba.navigator.entities.gpx;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "gpx", strict = false)
public class GpxEntity {

    @Element(name = "trk")
    public TrkEntity track;
}
