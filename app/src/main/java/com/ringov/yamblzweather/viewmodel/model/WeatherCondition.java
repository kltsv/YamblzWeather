package com.ringov.yamblzweather.viewmodel.model;

import android.support.annotation.StringRes;

import com.ringov.yamblzweather.R;

/**
 * Created by ringov on 15.07.17.
 */

public enum WeatherCondition {
    Cloudy(R.string.cloudy),
    Clear(R.string.clear),
    Rainy(R.string.rainy),
    Thunderstorm(R.string.thunderstorm),
    Snow(R.string.snow),
    Atmospherically(R.string.atmospherically),
    Extreme(R.string.extreme),
    Windy(R.string.windy),
    Calm(R.string.calm),
    Other(R.string.other);

    @StringRes
    private int friendlyName;

    WeatherCondition(@StringRes int friendlyName) {
        this.friendlyName = friendlyName;
    }

    @StringRes
    public int getFriendlyName() {
        return friendlyName;
    }
}
