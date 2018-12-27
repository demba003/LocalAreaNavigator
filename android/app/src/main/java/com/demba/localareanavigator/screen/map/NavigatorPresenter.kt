package com.demba.localareanavigator.screen.map

import android.content.Context
import android.location.Location

import com.demba.localareanavigator.R
import com.demba.localareanavigator.utils.FloorChangeDirections
import com.demba.localareanavigator.utils.NetworkUtils
import com.demba.navigator.Navigator
import com.demba.navigator.entities.GeoJson
import com.demba.navigator.models.Path

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class NavigatorPresenter internal constructor(private val view: NavigatorFragment.NavigatorView, placeName: String) {
    private var navigator: Navigator? = null
    private var currentFloor: Int = 0
    private var minFloor: Int = 0
    private var maxFloor: Int = 0
    private var route: Path? = null

    val waypoints: List<String>
        get() = navigator!!.waypointsNames

    init {
        if (!placeName.isEmpty()) {
            NetworkUtils
                    .getBackendService()
                    .getPlace(placeName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onSuccess = { place ->
                                navigator = Navigator.fromGeojson(place.data)
                                view.enableFab()
                            },
                            onError = { _ ->
                                view.showLoadingError()
                            }
                    )
        }
    }

    fun changeFloor(direction: FloorChangeDirections) {
        when (direction) {
            FloorChangeDirections.UP -> {
                view.setFloor(++currentFloor)
                view.setDirectionFabState(FloorChangeDirections.DOWN, true)
                if (currentFloor == maxFloor) view.setDirectionFabState(FloorChangeDirections.UP, false)
                view.showRoute(
                        GeoJson.encode(
                                route!!.oneFloorSubPath(currentFloor.toString())))
            }
            FloorChangeDirections.DOWN -> {
                view.setFloor(--currentFloor)
                view.setDirectionFabState(FloorChangeDirections.UP, true)
                if (currentFloor == minFloor) view.setDirectionFabState(FloorChangeDirections.DOWN, false)
                view.showRoute(
                        GeoJson.encode(
                                route!!.oneFloorSubPath(currentFloor.toString())))
            }
        }
    }

    fun showRoute(context: Context?, source: String, destination: String, location: Location?): Boolean {
        if (waypoints.contains(source) && waypoints.contains(destination)) {
            route = navigator!!.getShortestPath(source, destination)
            setupAndShowRoute()
            return true
        } else if (source == destination && !source.isEmpty()) {
            view.showDestinationReached()
            return false
        } else if (source == context?.getString(R.string.my_location)) {
            route = navigator!!.getShortestPath(location?.latitude.toString(), location?.longitude.toString(), destination)
            setupAndShowRoute()
            return true
        } else {
            view.showBadWaypointError()
            return false
        }
    }

    private fun setupAndShowRoute() {
        minFloor = route!!.minFloor
        maxFloor = route!!.maxFloor
        currentFloor = Integer.parseInt(route!!.points[0].floor)

        view.setFloor(currentFloor)
        view.setDirectionFabState(FloorChangeDirections.DOWN, minFloor < currentFloor)
        view.setDirectionFabState(FloorChangeDirections.UP, maxFloor > currentFloor)
        view.showRoute(
                GeoJson.encode(
                        route!!.oneFloorSubPath(route!!.points[0].floor)))
    }
}
