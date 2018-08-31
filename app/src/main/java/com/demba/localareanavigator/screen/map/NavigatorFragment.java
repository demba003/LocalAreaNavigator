package com.demba.localareanavigator.screen.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.demba.localareanavigator.R;
import com.demba.localareanavigator.utils.AssetLoader;
import com.demba.navigator.Navigator;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class NavigatorFragment extends Fragment implements OnMapReadyCallback {
    private NavigatorView view;
    private NavigatorPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        ButterKnife.bind(this, view);

        Mapbox.getInstance(getContext(), "pk.eyJ1IjoiZGVtYmEiLCJhIjoiY2pibWo3cW43M2I5eDM0cjY0eG4zY2JxZyJ9.SFBv4D82Ih54yJHF5U__BQ");
        MapView mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @OnClick(R.id.fab)
    public void showRouteDialog(View view) {
        this.view.showRouteDialog();
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.view = new NavigatorView(mapboxMap);
        presenter = new NavigatorPresenter(getContext(), this.view);
    }


    class NavigatorView {
        private AlertDialog findRouteDialog;
        private MapboxMap mapboxMap;
        private GeoJsonSource geoJsonSource;
        private LineLayer linesLayer;

        AutoCompleteTextView sourceTextView;
        AutoCompleteTextView destinationTextView;

        NavigatorView(MapboxMap mapboxMap) {
            this.mapboxMap = mapboxMap;
            initMapElements();
            buildRouteDialog();
        }

        private void initMapElements() {
            geoJsonSource = new GeoJsonSource("geoJsonSource", "");
            mapboxMap.addSource(geoJsonSource);

            linesLayer = new LineLayer("routeLines", "geoJsonSource");
            linesLayer.setProperties(
                    PropertyFactory.lineWidth(4f),
                    PropertyFactory.lineOpacity(0.7f)
            );
            mapboxMap.addLayer(linesLayer);
        }

        void showRouteDialog() {
            if (findRouteDialog != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, presenter.getWaypoints());
                sourceTextView.setAdapter(adapter);
                destinationTextView.setAdapter(adapter);
                findRouteDialog.show();
            }
        }

        private void buildRouteDialog() {
            final View findRouteView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.dialog_navigation, null);

            findRouteDialog = new AlertDialog.Builder(getContext()).create();
            findRouteDialog.setView(findRouteView);

            sourceTextView = findRouteView.findViewById(R.id.sourceTextView);
            destinationTextView = findRouteView.findViewById(R.id.destinationTextView);

            findRouteView
                    .findViewById(R.id.showRoute)
                    .setOnClickListener(v -> {
                        presenter.showRoute(sourceTextView.getText().toString(), destinationTextView.getText().toString());
                        findRouteDialog.dismiss();
                    });
        }

        void showRoute(String route) {
            geoJsonSource.setGeoJson(route);
        }
    }
}
