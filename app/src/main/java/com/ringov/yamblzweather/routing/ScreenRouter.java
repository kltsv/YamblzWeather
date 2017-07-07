package com.ringov.yamblzweather.routing;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.ringov.yamblzweather.about.AboutFragment;
import com.ringov.yamblzweather.exceptions.ExceptionFragment;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.main.WeatherFragment;
import com.ringov.yamblzweather.settings.SettingsFragment;

import java.util.HashMap;

/**
 * Created by ringov on 07.07.17.
 */

public class ScreenRouter {

    public enum Screen {
        Weather(new WeatherFragment()), Settings(new SettingsFragment()), About(new AboutFragment()), UNKNOWN(new ExceptionFragment());

        private static HashMap<Integer, Screen> screenMap;

        static {
            screenMap = new HashMap();
            screenMap.put(R.id.nav_weather, Weather);
            screenMap.put(R.id.nav_settings, Settings);
            screenMap.put(R.id.nav_about, About);
        }

        private Fragment mFragment;

        Screen(Fragment fragment) {
            this.mFragment = fragment;
        }

        public static Screen fromId(@IdRes int id) {
            Screen screen = screenMap.get(id);
            return screen != null ? screen : UNKNOWN;
        }

        public Fragment getFragment() {
            return mFragment;
        }
    }

}
