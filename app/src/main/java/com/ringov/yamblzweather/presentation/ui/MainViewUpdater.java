package com.ringov.yamblzweather.presentation.ui;

import com.ringov.yamblzweather.presentation.data.UIWeather;

/**
 * Created by ringov on 16.07.17.
 */

public interface MainViewUpdater {

    void onWeatherUpdate(UIWeather weather);
}
