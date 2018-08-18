package com.demba.localareanavigator;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.demba.navigator.Navigator;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String data = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" xmlns:gpsies=\"https://www.gpsies.com/GPX/1/0\" creator=\"GPSies https://www.gpsies.com - GPSies Track\" version=\"1.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd https://www.gpsies.com/GPX/1/0 https://www.gpsies.com/gpsies.xsd\">\n" +
                "  <metadata>\n" +
                "    <name>GPSies Track</name>\n" +
                "    <link href=\"https://www.gpsies.com/\">\n" +
                "      <text>GPSies Track on GPSies.com</text>\n" +
                "    </link>\n" +
                "    <time>2018-08-09T22:13:52Z</time>\n" +
                "  </metadata>\n" +
                "  <trk>\n" +
                "    <name>GPSies Track on GPSies.com</name>\n" +
                "    <trkseg>\n" +
                "      <trkpt lat=\"50.07247282\" lon=\"19.94059592\">\n" +
                "        <ele>214.00000</ele>\n" +
                "        <time>2010-01-01T00:00:00Z</time>\n" +
                "      </trkpt>\n" +
                "      <trkpt lat=\"50.07182553\" lon=\"19.94024723\">\n" +
                "        <ele>214.00000</ele>\n" +
                "        <time>2010-01-01T00:00:27Z</time>\n" +
                "      </trkpt>\n" +
                "      <trkpt lat=\"50.07146057\" lon=\"19.94178682\">\n" +
                "        <ele>218.00000</ele>\n" +
                "        <time>2010-01-01T00:01:09Z</time>\n" +
                "      </trkpt>\n" +
                "      <trkpt lat=\"50.07208031\" lon=\"19.94218915\">\n" +
                "        <ele>216.00000</ele>\n" +
                "        <time>2010-01-01T00:01:36Z</time>\n" +
                "      </trkpt>\n" +
                "      <trkpt lat=\"50.07243150\" lon=\"19.94062274\">\n" +
                "        <ele>214.00000</ele>\n" +
                "        <time>2010-01-01T00:02:19Z</time>\n" +
                "      </trkpt>\n" +
                "      <trkpt lat=\"50.07183586\" lon=\"19.94029551\">\n" +
                "        <ele>213.00000</ele>\n" +
                "        <time>2010-01-01T00:02:44Z</time>\n" +
                "      </trkpt>\n" +
                "      <trkpt lat=\"50.07131252\" lon=\"19.94000047\">\n" +
                "        <ele>217.00000</ele>\n" +
                "        <time>2010-01-01T00:03:06Z</time>\n" +
                "      </trkpt>\n" +
                "    </trkseg>\n" +
                "  </trk>\n" +
                "</gpx>\n";



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
