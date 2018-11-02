package com.demba.localareanavigator.screen.main

import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.demba.localareanavigator.R
import com.demba.localareanavigator.screen.browseplaces.BrowsePlacesFragment
import com.demba.localareanavigator.screen.map.NavigatorFragment
import com.mikepenz.aboutlibraries.LibsBuilder

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, NavigatorFragment())
                .commit()

        setActiveTab(R.id.view_map)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun setActiveTab(id: Int) {
        navigationView.setCheckedItem(id)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_map -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_frame, NavigatorFragment())
                    .commit()
            R.id.browse_places -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_frame, BrowsePlacesFragment())
                    .commit()
            R.id.make_location -> {
            }
            R.id.legal_notices -> supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_frame, LibsBuilder()
                            .withLicenseShown(true)
                            .withVersionShown(true)
                            .withAboutIconShown(true)
                            .withAboutDescription(resources.getString(R.string.libs_info))
                            .supportFragment())
                    .commit()
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
