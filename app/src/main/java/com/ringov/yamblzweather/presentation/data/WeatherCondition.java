package com.ringov.yamblzweather.presentation.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.ringov.yamblzweather.R;

public enum WeatherCondition {

    Cloudy(R.string.cond_cloudy, R.drawable.weather_clouds),
    Clear(R.string.cond_clear, R.drawable.weather_clear),
    Rainy(R.string.cond_rainy, R.drawable.weather_rainy),
    Thunderstorm(R.string.cond_thunderstorm, R.drawable.weather_storm),
    Snow(R.string.cond_snow, R.drawable.weather_snow),
    Atmospherically(R.string.cond_atmospherically, R.drawable.weather_extreme),
    Extreme(R.string.cond_extreme, R.drawable.weather_extreme),
    Windy(R.string.cond_windy, R.drawable.weather_wind),
    Calm(R.string.cond_calm, R.drawable.weather_clear),
    Other(R.string.cond_other, R.drawable.weather_other);

    @StringRes
    private final int friendlyName;
    @DrawableRes
    private final int image;

    WeatherCondition(@StringRes int friendlyName, @DrawableRes int conditionImage) {
        this.friendlyName = friendlyName;
        this.image = conditionImage;
    }

    @StringRes
    public int getFriendlyName() {
        return friendlyName;
    }

    @DrawableRes
    public int getConditionImage() {
        return image;
    }
}
