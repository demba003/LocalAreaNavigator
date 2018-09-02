package com.demba.localareanavigator.screen.map;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.demba.localareanavigator.R;
import com.demba.localareanavigator.utils.FloorChangeDirections;
import com.demba.localareanavigator.utils.SnackbarUtils;
import com.github.clans.fab.FloatingActionButton;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NavigatorFragment extends Fragment implements OnMapReadyCallback {
    private NavigatorView view;
    private NavigatorPresenter presenter;
    MapView mapView;
    View fragmentView;
    View findRouteView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_map, container, false);

        Mapbox.getInstance(getContext(), "pk.eyJ1IjoiZGVtYmEiLCJhIjoiY2pibWo3cW43M2I5eDM0cjY0eG4zY2JxZyJ9.SFBv4D82Ih54yJHF5U__BQ");
        mapView = fragmentView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return fragmentView;
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.view = new NavigatorView(mapboxMap);
        presenter = new NavigatorPresenter(getContext(), this.view);
    }


    class NavigatorView {
        private MapboxMap mapboxMap;
        private LocationLayerPlugin locationPlugin;
        private AlertDialog findRouteDialog;
        private GeoJsonSource geoJsonSource;
        private LineLayer linesLayer;

        AutoCompleteTextView sourceTextView;
        AutoCompleteTextView destinationTextView;

        @BindView(R.id.fab_up) FloatingActionButton fab_up;
        @BindView(R.id.fab_down) FloatingActionButton fab_down;
        @BindView(R.id.floor) TextView floor;

        NavigatorView(MapboxMap mapboxMap) {
            this.mapboxMap = mapboxMap;
            ButterKnife.bind(this, fragmentView);
            initMapElements();
            buildRouteDialog();
            setFloor(0);
        }

        @OnClick(R.id.fab)
        public void showRouteDialog(View view) {
            showRouteDialog();
        }

        @OnClick(R.id.fab_up)
        public void floorUp(View view) {
            presenter.changeFloor(FloorChangeDirections.UP);
        }

        @OnClick(R.id.fab_down)
        public void floorDown(View view) {
            presenter.changeFloor(FloorChangeDirections.DOWN);
        }

        void setFloor(int floorNumber) {
            if (floorNumber == 0) {
                floor.setText(getString(R.string.ground_floor));
            } else {
                floor.setText(String.valueOf(floorNumber));
            }
        }

        void setDirectionFabState(FloorChangeDirections direction, boolean enabled) {
            switch (direction) {
                case DOWN:
                    fab_down.setEnabled(enabled);
                    break;
                case UP:
                    fab_up.setEnabled(enabled);
                    break;
            }
        }

        private void initMapElements() {
            geoJsonSource = new GeoJsonSource("geoJsonSource", "");
            mapboxMap.addSource(geoJsonSource);

            linesLayer = new LineLayer("routeLines", "geoJsonSource");
            linesLayer.setProperties(
                    PropertyFactory.lineWidth(4f),
                    PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
                    PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND)
            );
            mapboxMap.addLayer(linesLayer);

            locationPlugin = new LocationLayerPlugin(mapView, mapboxMap);
            locationPlugin.setRenderMode(RenderMode.GPS);
            getLifecycle().addObserver(locationPlugin);
        }

        void showRouteDialog() {
            if (findRouteDialog != null && getContext() != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dialog_navigation_dropdown, presenter.getWaypoints());
                sourceTextView.setAdapter(adapter);
                destinationTextView.setAdapter(adapter);
                findRouteDialog.show();
            }
        }

        private void buildRouteDialog() {
            findRouteView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.dialog_navigation, null);

            findRouteDialog = new AlertDialog.Builder(getContext()).create();
            findRouteDialog.setView(findRouteView);

            sourceTextView = findRouteView.findViewById(R.id.sourceTextView);
            destinationTextView = findRouteView.findViewById(R.id.destinationTextView);

            findRouteView
                    .findViewById(R.id.showRoute)
                    .setOnClickListener(v -> {
                        if (presenter.showRoute(getContext(), sourceTextView.getText().toString(), destinationTextView.getText().toString())) {
                            findRouteDialog.dismiss();
                            fab_up.setVisibility(View.VISIBLE);
                            fab_down.setVisibility(View.VISIBLE);
                            floor.setVisibility(View.VISIBLE);
                            moveCameraToRoute();

                        }
                    });

            findRouteView
                    .findViewById(R.id.myLocationSource)
                    .setOnClickListener(v -> sourceTextView.setText(R.string.my_location));

            findRouteView
                    .findViewById(R.id.myLocationDestination)
                    .setOnClickListener(v -> destinationTextView.setText(R.string.my_location));
        }

        void showRoute(String route) {
            geoJsonSource.setGeoJson(route);
        }

        private void moveCameraToRoute() {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            geoJsonSource
                    .querySourceFeatures(null)
                    .forEach(feature -> {
                        try {
                            Point point = ((Point) feature.geometry());
                            builder.include(
                                    new LatLng(
                                            point.latitude(),
                                            point.longitude()
                                    )
                            );
                        } catch (ClassCastException ignored) {}
                    });

            LatLngBounds latLngBounds = builder.build();
            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50));
        }

        void showDestinationReached() {
            SnackbarUtils.showSuccess(getContext(), findRouteView, getString(R.string.destination_reached), Snackbar.LENGTH_SHORT);
        }

        void showBadWaypointError() {
            SnackbarUtils.showError(getContext(), findRouteView, getString(R.string.bad_waypoint), Snackbar.LENGTH_SHORT);
        }
    }
}
