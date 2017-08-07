package com.ringov.yamblzweather.presentation.entity;

import android.support.annotation.StringRes;

import com.ringov.yamblzweather.R;

public enum  WindDirection {

    North(R.string.wthr_dtls_wind_direction_north, 0),
    NorthEast(R.string.wthr_dtls_wind_direction_north_east, 45),
    East(R.string.wthr_dtls_wind_direction_east, 90),
    SouthEast(R.string.wthr_dtls_wind_direction_south_east, 135),
    South(R.string.wthr_dtls_wind_direction_south, 180),
    SouthWest(R.string.wthr_dtls_wind_direction_south_west, 225),
    West(R.string.wthr_dtls_wind_direction_west, 270),
    NorthWest(R.string.wthr_dtls_wind_direction_north_west, 315);

    @StringRes
    private final int friendlyName;

    private final int rotation;

    WindDirection(int friendlyName, int rotation) {
        this.friendlyName = friendlyName;
        this.rotation = rotation;
    }
}
