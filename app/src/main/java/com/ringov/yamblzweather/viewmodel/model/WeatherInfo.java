package com.ringov.yamblzweather.viewmodel.model;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherInfo {

    private double temperature;

    public double getTemperature() {
        return temperature;
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

        public WeatherInfo build() {
            return weather;
        }
    }
}
