package com.ringov.yamblzweather.presentation.ui.main;

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
import com.ringov.yamblzweather.presentation.ui.main.about.AboutFragment;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationFragment;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @IdRes
    private static final int FRAGMENT_CONTAINER = R.id.container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    private boolean toolBarNavigationListenerIsRegistered = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
        initializeDrawer();

        if (savedInstanceState == null) {
            navigateToWeatherScreen();
        } else if (isNotOnWeatherScreen()) {
            showBackButton(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (toolBarNavigationListenerIsRegistered) {
            navigateToWeatherScreen();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_weather:
                navigateToWeatherScreen();
                break;
            case R.id.nav_location:
                navigateToLocationScreen();
                break;
            case R.id.nav_about:
                navigateToAboutScreen();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initializeDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * See https://stackoverflow.com/questions/36579799/
     */
    private void showBackButton(boolean enable) {
        if (enable) {
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!toolBarNavigationListenerIsRegistered) {
                drawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());

                toolBarNavigationListenerIsRegistered = true;
            }
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(null);
            toolBarNavigationListenerIsRegistered = false;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private boolean isNotOnWeatherScreen() {
        return getSupportFragmentManager().findFragmentByTag(WeatherFragment.TAG) == null;
    }

    private void navigateToWeatherScreen() {
        if (isNotOnWeatherScreen()) {
            showBackButton(false);
            replaceFragment(WeatherFragment.newInstance(), FRAGMENT_CONTAINER);
        }
    }

    private void navigateToLocationScreen() {
        showBackButton(true);
        replaceFragment(LocationFragment.newInstance(), FRAGMENT_CONTAINER);
    }

    private void navigateToAboutScreen() {
        showBackButton(true);
        replaceFragment(AboutFragment.newInstance(), FRAGMENT_CONTAINER);
    }
}
