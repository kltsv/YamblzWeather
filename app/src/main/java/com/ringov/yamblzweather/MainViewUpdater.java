package com.ringov.yamblzweather;

import com.ringov.yamblzweather.viewmodel.data.UIWeather;

/**
 * Created by ringov on 16.07.17.
 */

public interface MainViewUpdater {
    void onWeatherUpdate(UIWeather weather);
}
