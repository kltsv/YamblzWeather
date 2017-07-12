package com.ringov.yamblzweather.routing;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.ringov.yamblzweather.about.AboutFragment;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.main.WeatherFragment;
import com.ringov.yamblzweather.settings.SettingsFragment;
import java.util.HashMap;

/**
 * Created by ringov on 07.07.17.
 */

public class ScreenRouter {

    public enum Screen {
        Weather(R.id.nav_weather, new WeatherFragment()),
        Settings(R.id.nav_settings, new SettingsFragment()),
        About(R.id.nav_about, new AboutFragment());

        private static HashMap<Integer, Screen> screenMap;

        static {
            screenMap = new HashMap();
            screenMap.put(Weather.getId(), Weather);
            screenMap.put(Settings.getId(), Settings);
            screenMap.put(About.getId(), About);
        }

        @IdRes
        private int mId;
        private Fragment mFragment;

        Screen(@IdRes int id, Fragment fragment) {
            this.mId = id;
            this.mFragment = fragment;
        }

        public static Screen fromId(@IdRes int id) {
            Screen screen = screenMap.get(id);
            return screen;
        }

        public Fragment getFragment() {
            return mFragment;
        }

        @IdRes
        public int getId() {
            return mId;
        }
    }

}
