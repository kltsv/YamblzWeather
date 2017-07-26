package com.ringov.yamblzweather.presentation.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.base.BaseActivity;
import com.ringov.yamblzweather.presentation.ui.about.AboutFragment;
import com.ringov.yamblzweather.presentation.ui.location.LocationFragment;
import com.ringov.yamblzweather.presentation.ui.settings.SettingsFragment;
import com.ringov.yamblzweather.presentation.ui.weather.WeatherFragment;

import butterknife.BindView;

/**
 * Created by ringov on 07.07.17.
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @IdRes
    private static final int FRAGMENT_CONTAINER = R.id.container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
        initializeDrawer();

        if (savedInstanceState == null) {
            replaceFragment(new WeatherFragment(), FRAGMENT_CONTAINER);
        }
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initializeDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
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
            case R.id.nav_weather:
                replaceFragment(WeatherFragment.newInstance(), FRAGMENT_CONTAINER);
                break;
            case R.id.nav_location:
                replaceFragment(LocationFragment.newInstance(), FRAGMENT_CONTAINER);
                break;
            case R.id.nav_settings:
                replaceFragment(SettingsFragment.newInstance(), FRAGMENT_CONTAINER);
                break;
            case R.id.nav_about:
                replaceFragment(AboutFragment.newInstance(), FRAGMENT_CONTAINER);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
