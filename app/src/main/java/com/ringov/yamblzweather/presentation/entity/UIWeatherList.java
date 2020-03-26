package com.ringov.yamblzweather.presentation.entity;

public class UIWeatherList {

    private WeatherCondition condition;
    private float tempMax;
    private float tempMin;
    private long time;

    public WeatherCondition getCondition() {
        return condition;
    }

    public float getTempMax() {
        return tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public long getTime() {
        return time;
    }

    public static class Builder {

        private UIWeatherList weather;

        public Builder() {
            weather = new UIWeatherList();
        }

        public Builder condition(WeatherCondition condition) {
            weather.condition = condition;
            return this;
        }

        public Builder tempMax(float t) {
            weather.tempMax = t;
            return this;
        }

        public Builder tempMin(float t) {
            weather.tempMin = t;
            return this;
        }

        public Builder time(long time) {
            weather.time = time;
            return this;
        }

        public UIWeatherList build() {
            return weather;
        }
    }
}
