package com.ringov.yamblzweather.routing;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.ui.about.AboutFragment;
import com.ringov.yamblzweather.presentation.ui.location.LocationFragment;
import com.ringov.yamblzweather.presentation.ui.settings.SettingsFragment;
import com.ringov.yamblzweather.presentation.ui.weather.WeatherFragment;

import java.util.HashMap;

/**
 * Created by ringov on 15.07.17.
 */

public enum Screen {

    Weather(R.id.nav_weather, ((manager, container) -> {
        if (manager.findFragmentByTag(WeatherFragment.TAG) == null) {
            openFragment(manager, container, new WeatherFragment(), WeatherFragment.TAG);
        }
    })),
    Location(R.id.nav_location, ((manager, container) -> {
        if (manager.findFragmentByTag(LocationFragment.TAG) == null) {
            openFragment(manager, container, new LocationFragment(), LocationFragment.TAG);
        }
    })),
    Settings(R.id.nav_settings, ((manager, container) -> {
        if (manager.findFragmentByTag(SettingsFragment.TAG) == null) {
            openFragment(manager, container, new SettingsFragment(), SettingsFragment.TAG);
        }
    })),
    About(R.id.nav_about, ((manager, container) -> {
        if (manager.findFragmentByTag(AboutFragment.TAG) == null) {
            openFragment(manager, container, new AboutFragment(), AboutFragment.TAG);
        }
    }));

    private static void openFragment(FragmentManager manager, @IdRes int container, Fragment fragment, String tag) {
        manager.beginTransaction().replace(container, fragment, tag).commit();
    }

    private static HashMap<Integer, Screen> screenMap;

    static {
        screenMap = new HashMap();
        screenMap.put(Weather.getId(), Weather);
        screenMap.put(Location.getId(), Location);
        screenMap.put(Settings.getId(), Settings);
        screenMap.put(About.getId(), About);
    }

    @IdRes
    private int id;
    private Destination destination;

    Screen(@IdRes int id, Destination destination) {
        this.id = id;
        this.destination = destination;
    }

    public static Screen fromId(@IdRes int id) {
        return screenMap.get(id);
    }

    @IdRes
    public int getId() {
        return id;
    }

    public void open(FragmentManager manager, @IdRes int container) {
        destination.open(manager, container);
    }

    private interface Destination {

        void open(FragmentManager manager, @IdRes int container);
    }
}
