package com.demba.localareanavigator.screen.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.demba.localareanavigator.R;
import com.demba.localareanavigator.screen.browseplaces.BrowsePlacesFragment;
import com.demba.localareanavigator.screen.map.NavigatorFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private final RxPermissions rxPermissions = new RxPermissions(this);

    private Unbinder unbinder;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        rxPermissions.
                request(Manifest.permission.ACCESS_FINE_LOCATION).
                doOnComplete(() -> {
                    System.out.println(rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION));
                })
                .subscribe();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_map:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new NavigatorFragment())
                        .commit();
                break;
            case R.id.browse_places:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new BrowsePlacesFragment())
                        .commit();
                break;
            case R.id.make_location:

                break;
            case R.id.legal_notices:

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
