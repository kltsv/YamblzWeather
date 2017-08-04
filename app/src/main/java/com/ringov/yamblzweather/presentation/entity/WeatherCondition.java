package com.ringov.yamblzweather.presentation.entity;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.ringov.yamblzweather.R;

public enum WeatherCondition {

    Storm(R.string.cond_storm, R.drawable.im_storm),
    LightRain(R.string.cond_light_rain, R.drawable.im_light_rain),
    Rain(R.string.cond_rain, R.drawable.im_rain),
    Snow(R.string.cond_snow, R.drawable.im_snow),
    Fog(R.string.cond_fog, R.drawable.im_fog),
    Clear(R.string.cond_clear, R.drawable.im_clear),
    LightClouds(R.string.cond_light_clouds, R.drawable.im_light_clouds),
    Cloudy(R.string.cond_cloudy, R.drawable.im_clouds);

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
