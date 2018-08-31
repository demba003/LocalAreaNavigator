package com.demba.localareanavigator.screen.map;

import android.content.Context;

import com.demba.localareanavigator.utils.AssetLoader;
import com.demba.navigator.Navigator;

import java.util.List;

public class NavigatorPresenter {
    private Navigator navigator;
    private NavigatorFragment.NavigatorView view;


    NavigatorPresenter(Context context, NavigatorFragment.NavigatorView view) {
        navigator =  Navigator.fromGeojson(AssetLoader.loadGeoJson(context, "kampus.min.geojson"));
        this.view = view;
    }

    public boolean showRoute(Context context, String source, String destination) {
        if (getWaypoints().contains(source) && getWaypoints().contains(destination)) {
            view.showRoute(navigator.getShortestPathGeoJson(source, destination));
            return true;
        } else if (source.equals(destination) && !source.isEmpty()) {
            view.showDestinationReached();
            return false;
        } else {
            view.showBadWaypointError();
            return false;
        }
    }

    public List<String> getWaypoints() {
        return navigator.getWaypointsNames();
    }
}
