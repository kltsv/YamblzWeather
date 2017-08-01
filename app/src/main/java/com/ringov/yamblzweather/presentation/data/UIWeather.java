package com.ringov.yamblzweather.presentation.data;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class UIWeather {

    private long time;
    private float temperature;
    private WeatherCondition condition;

    public float getTemperature() {
        return temperature;
    }

    @StringRes
    public int getConditionName() {
        return condition.getFriendlyName();
    }

    public long getTime() {
        return time;
    }

    @DrawableRes
    public int getConditionImage() {
        return condition.getConditionImage();
    }

    public static class Builder {

        private UIWeather weather;

        public Builder() {
            weather = new UIWeather();
        }

        public Builder temperature(float t) {
            weather.temperature = t;
            return this;
        }

        public Builder weatherCondition(WeatherCondition condition) {
            weather.condition = condition;
            return this;
        }

        public UIWeather build() {
            return weather;
        }

        public Builder time(long time) {
            weather.time = time;
            return this;
        }
    }
}
