package com.ringov.yamblzweather.viewmodel.data;

import android.support.annotation.StringRes;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherInfo {

    private long time;
    private float temperature;
    private WeatherCondition condition;

    public float getTemperature() {
        return temperature;
    }

    @StringRes
    public int getCondition() {
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

        public Builder temperature(float t) {
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
