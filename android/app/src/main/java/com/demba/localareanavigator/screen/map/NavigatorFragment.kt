package com.demba.localareanavigator.screen.map

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.demba.localareanavigator.R
import com.demba.localareanavigator.network.models.Place
import com.demba.localareanavigator.utils.FloorChangeDirections
import com.demba.localareanavigator.utils.SnackbarUtils
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode
import com.mapbox.mapboxsdk.style.layers.LineLayer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource

import kotlinx.android.synthetic.main.fragment_map.*

class NavigatorFragment : Fragment(), OnMapReadyCallback {

    private var presenter: NavigatorPresenter? = null
    private var findRouteView: View? = null
    private var gpsAccess = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Mapbox.getInstance(context!!, "pk.eyJ1IjoiZGVtYmEiLCJhIjoiY2pibWo3cW43M2I5eDM0cjY0eG4zY2JxZyJ9.SFBv4D82Ih54yJHF5U__BQ")
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }


    fun setTitle(title: String) {
        activity?.title = "${getString(R.string.map)} - $title"
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        presenter = NavigatorPresenter(NavigatorView(mapboxMap), arguments?.getString(PLACE_EXTRA) ?: "")
        arguments?.getString(PLACE_EXTRA)?.let {
            setTitle(it)
        }
    }

    internal inner class NavigatorView(private val mapboxMap: MapboxMap) {
        private var locationPlugin: LocationLayerPlugin? = null
        private lateinit var findRouteDialog: AlertDialog
        private var geoJsonSource: GeoJsonSource? = null
        private var linesLayer: LineLayer? = null
        var sourceTextView: AutoCompleteTextView? = null
        var destinationTextView: AutoCompleteTextView? = null

        init {
            checkPermissions()
            initMapElements()
            buildRouteDialog()

            fabUp.setOnClickListener { presenter?.changeFloor(FloorChangeDirections.UP) }
            fabDown.setOnClickListener { presenter?.changeFloor(FloorChangeDirections.DOWN) }
            fab.setOnClickListener { showRouteDialog() }
        }

        private fun checkPermissions() {
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1234)
            } else {
                gpsAccess = true
                locationPlugin = LocationLayerPlugin(mapView!!, mapboxMap)
                locationPlugin!!.renderMode = RenderMode.COMPASS
                lifecycle.addObserver(locationPlugin!!)
            }
        }

        fun setFloor(floorNumber: Int) {
            if (floorNumber == 0) {
                floor.text = getString(R.string.ground_floor)
            } else {
                floor.text = floorNumber.toString()
            }
        }

        fun setDirectionFabState(direction: FloorChangeDirections, enabled: Boolean) {
            when (direction) {
                FloorChangeDirections.DOWN -> fabDown.isEnabled = enabled
                FloorChangeDirections.UP -> fabUp.isEnabled = enabled
            }
        }

        fun enableFab() {
            fab.visibility = View.VISIBLE
        }

        private fun initMapElements() {
            geoJsonSource = GeoJsonSource("geoJsonSource", "")
            mapboxMap.addSource(geoJsonSource!!)

            linesLayer = LineLayer("routeLines", "geoJsonSource")
            linesLayer!!.setProperties(
                    PropertyFactory.lineWidth(4f),
                    PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                    PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND)
            )
            mapboxMap.addLayer(linesLayer!!)
        }

        fun showRouteDialog() {
            val adapter = ArrayAdapter(context!!, R.layout.dialog_navigation_dropdown, presenter!!.waypoints)
            sourceTextView!!.setAdapter(adapter)
            destinationTextView!!.setAdapter(adapter)
            findRouteDialog.show()
        }

        private fun buildRouteDialog() {
            findRouteView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.dialog_navigation, null)

            findRouteDialog = AlertDialog.Builder(context!!)
                    .setView(findRouteView)
                    .create()

            findRouteView!!
                    .findViewById<Button>(R.id.showRoute)
                    .setOnClickListener { _ ->
                        if (presenter!!.showRoute(context, sourceTextView?.text.toString(), destinationTextView?.text.toString(), locationPlugin?.lastKnownLocation)) {
                            findRouteDialog.dismiss()
                            fabUp.visibility = View.VISIBLE
                            fabDown.visibility = View.VISIBLE
                            floor.visibility = View.VISIBLE
                            moveCameraToRoute()
                        }
                    }

            sourceTextView = findRouteView!!.findViewById(R.id.sourceTextView)
            destinationTextView = findRouteView!!.findViewById(R.id.destinationTextView)

            findRouteView!!
                    .findViewById<ImageView>(R.id.myLocationSource)
                    .setOnClickListener { _ -> sourceTextView!!.setText(R.string.my_location) }
            findRouteView!!
                    .findViewById<ImageView>(R.id.myLocationDestination)
                    .setOnClickListener { _ -> destinationTextView!!.setText(R.string.my_location) }
        }

        fun showRoute(route: String) {
            geoJsonSource!!.setGeoJson(route)
        }

        private fun moveCameraToRoute() {
            val builder = LatLngBounds.Builder()

            geoJsonSource!!
                    .querySourceFeatures(null)
                    .forEach { feature ->
                        try {
                            val point = feature.geometry() as Point
                            builder.include(LatLng(point.latitude(), point.longitude()))
                        } catch (ignored: ClassCastException) {}
                    }

            val latLngBounds = builder.build()
            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50))
        }

        fun showDestinationReached() {
            SnackbarUtils.showSuccess(context!!, findRouteView!!, getString(R.string.destination_reached), Snackbar.LENGTH_SHORT)
        }

        fun showBadWaypointError() {
            SnackbarUtils.showError(context!!, findRouteView!!, getString(R.string.bad_waypoint), Snackbar.LENGTH_SHORT)
        }

        fun showLoadingError() {
            Toast.makeText(context, R.string.loading_error, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val PLACE_EXTRA = "place"

        fun getFragment(place: Place): NavigatorFragment {
            val navigatorFragment = NavigatorFragment()
            val arguments = Bundle()
            arguments.putString(PLACE_EXTRA, place.name)
            navigatorFragment.arguments = arguments
            return navigatorFragment
        }
    }
}
