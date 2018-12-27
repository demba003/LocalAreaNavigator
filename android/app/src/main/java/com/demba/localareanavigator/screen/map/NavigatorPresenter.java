package com.demba.localareanavigator.screen.map;

import android.content.Context;
import android.location.Location;
import android.widget.Toast;

import com.demba.localareanavigator.R;
import com.demba.localareanavigator.utils.FloorChangeDirections;
import com.demba.localareanavigator.utils.NetworkUtils;
import com.demba.navigator.Navigator;
import com.demba.navigator.entities.GeoJson;
import com.demba.navigator.models.Path;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NavigatorPresenter {
    private Navigator navigator;
    private NavigatorFragment.NavigatorView view;
    private int currentFloor;
    private int minFloor, maxFloor;
    private Path route = null;

    NavigatorPresenter(Context context, NavigatorFragment.NavigatorView view, String placeName) {
        if (!placeName.isEmpty()) {
            NetworkUtils.INSTANCE
                    .getBackendService()
                    .getPlace(placeName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess(place -> {
                        navigator =  Navigator.fromGeojson(place.getData());
                        view.enableFab();
                    })
                    .doOnError(throwable -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show())
                    .subscribe();
        }
        this.view = view;
    }

    public void changeFloor(FloorChangeDirections direction) {
        switch (direction) {
            case UP:
                view.setFloor(++currentFloor);
                view.setDirectionFabState(FloorChangeDirections.DOWN, true);
                if(currentFloor == maxFloor) view.setDirectionFabState(FloorChangeDirections.UP, false);
                view.showRoute(
                        GeoJson.encode(
                                route.oneFloorSubPath(String.valueOf(currentFloor))));
                break;
            case DOWN:
                view.setFloor(--currentFloor);
                view.setDirectionFabState(FloorChangeDirections.UP, true);
                if(currentFloor == minFloor) view.setDirectionFabState(FloorChangeDirections.DOWN, false);
                view.showRoute(
                        GeoJson.encode(
                                route.oneFloorSubPath(String.valueOf(currentFloor))));
                break;
        }
    }

    public boolean showRoute(Context context, String source, String destination, Location location) {
        if (getWaypoints().contains(source) && getWaypoints().contains(destination)) {
            route = navigator.getShortestPath(source, destination);
            setupAndShowRoute();
            return true;
        } else if (source.equals(destination) && !source.isEmpty()) {
            view.showDestinationReached();
            return false;
        } else if (source.equals(context.getString(R.string.my_location))) {
            route = navigator.getShortestPath(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), destination);
            setupAndShowRoute();
            return true;
        } else {
            view.showBadWaypointError();
            return false;
        }
    }

    public List<String> getWaypoints() {
        return navigator.getWaypointsNames();
    }

    private void setupAndShowRoute() {
        minFloor = route.getMinFloor();
        maxFloor = route.getMaxFloor();
        currentFloor = Integer.parseInt(route.getPoints().get(0).getFloor());

        view.setFloor(currentFloor);
        view.setDirectionFabState(FloorChangeDirections.DOWN, minFloor < currentFloor);
        view.setDirectionFabState(FloorChangeDirections.UP, maxFloor > currentFloor);
        view.showRoute(
                GeoJson.encode(
                        route.oneFloorSubPath(route.getPoints().get(0).getFloor())));
    }
}
