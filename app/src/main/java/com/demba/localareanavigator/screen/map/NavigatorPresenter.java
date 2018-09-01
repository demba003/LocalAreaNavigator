package com.demba.localareanavigator.screen.map;

import android.content.Context;

import com.demba.localareanavigator.R;
import com.demba.localareanavigator.utils.AssetLoader;
import com.demba.localareanavigator.utils.FloorChangeDirections;
import com.demba.navigator.Navigator;

import java.util.List;

public class NavigatorPresenter {
    private Navigator navigator;
    private NavigatorFragment.NavigatorView view;
    private int currentFloor = 0;

    NavigatorPresenter(Context context, NavigatorFragment.NavigatorView view) {
        navigator =  Navigator.fromGeojson(AssetLoader.loadGeoJson(context, "kampus.min.geojson"));
        this.view = view;
    }

    public void changeFloor(FloorChangeDirections direction) {
        switch (direction) {
            case UP:
                view.setFloor(++currentFloor);
                view.setDirectionFabState(FloorChangeDirections.DOWN, true);
                if(currentFloor == 3) view.setDirectionFabState(FloorChangeDirections.UP, false);
                break;
            case DOWN:
                view.setFloor(--currentFloor);
                view.setDirectionFabState(FloorChangeDirections.UP, true);
                if(currentFloor == -3) view.setDirectionFabState(FloorChangeDirections.DOWN, false);
                break;
        }
    }

    public boolean showRoute(Context context, String source, String destination) {
        if (getWaypoints().contains(source) && getWaypoints().contains(destination)) {
            view.showRoute(navigator.getShortestPathGeoJson(source, destination));
            return true;
        } else if (source.equals(destination) && !source.isEmpty()) {
            view.showDestinationReached();
            return false;
        } else if (source.equals(context.getString(R.string.my_location))) {
            view.showRoute(navigator.getShortestPathGeoJson("50.071289", "19.941174", destination));
            return true;
        } else {
            view.showBadWaypointError();
            return false;
        }
    }

    public List<String> getWaypoints() {
        return navigator.getWaypointsNames();
    }
}
