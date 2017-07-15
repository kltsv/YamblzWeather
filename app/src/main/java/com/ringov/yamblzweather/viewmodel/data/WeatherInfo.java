package com.ringov.yamblzweather.viewmodel.data;

import android.support.annotation.StringRes;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherInfo {

    private long time;
    private double temperature;
    private WeatherCondition condition;

    public double getTemperature() {
        return temperature;
    }

    @StringRes
    public int getConditions() {
        return condition.getFriendlyName();
    }

    public long getTime() {
        return time;
    }

    public static class Builder {

        private WeatherInfo weather;

        public Builder() {
            weather = new WeatherInfo();
        }

        public Builder temperature(double t) {
            weather.temperature = t;
            return this;
        }

        public Builder weatherCondition(WeatherCondition condition) {
            weather.condition = condition;
            return this;
        }

        public WeatherInfo build() {
            return weather;
        }

        public Builder time(long time) {
            weather.time = time;
            return this;
        }
    }
}