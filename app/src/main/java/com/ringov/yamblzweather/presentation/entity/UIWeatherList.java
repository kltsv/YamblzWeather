package com.ringov.yamblzweather.presentation.entity;

public class UIWeatherList {

    private WeatherCondition condition;
    private float temperature;
    private long time;

    public WeatherCondition getCondition() {
        return condition;
    }

    public float getTemperature() {
        return temperature;
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

        public Builder temperature(float t) {
            weather.temperature = t;
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
