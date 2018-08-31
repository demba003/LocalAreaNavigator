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

    public void showRoute(String source, String destination) {
        view.showRoute(navigator.getShortestPathGeoJson(source, destination));
    }

    public List<String> getWaypoints() {
        return navigator.getWaypointsNames();
    }
}
