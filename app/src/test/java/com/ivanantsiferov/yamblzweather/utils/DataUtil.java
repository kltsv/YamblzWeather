package com.ivanantsiferov.yamblzweather.utils;

import com.ringov.yamblzweather.data.db.data.DBWeather;

public final class DataUtil {

    private DataUtil() {
    }

    public static DBWeather getDBWeather() {
        return new DBWeather
                .Builder(System.currentTimeMillis())
                .temperature(25f)
                .weatherCondition(200)
                .build();
    }
}
