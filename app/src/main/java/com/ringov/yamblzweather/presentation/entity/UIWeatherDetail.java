package com.ringov.yamblzweather.presentation.entity;

public class UIWeatherDetail {

    private WeatherCondition condition;
    private float temperature;
    private long time;
    private int humidity;
    private float pressure;
    private float windSpeed;
    private float windDegree;

    public WeatherCondition getCondition() {
        return condition;
    }

    public float getTemperature() {
        return temperature;
    }

    public long getTime() {
        return time;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getWindDegree() {
        return windDegree;
    }

    public static class Builder {

        private UIWeatherDetail weather;

        public Builder() {
            weather = new UIWeatherDetail();
        }

        public UIWeatherDetail.Builder condition(WeatherCondition condition) {
            weather.condition = condition;
            return this;
        }

        public UIWeatherDetail.Builder temperature(float t) {
            weather.temperature = t;
            return this;
        }

        public UIWeatherDetail.Builder time(long time) {
            weather.time = time;
            return this;
        }

        public UIWeatherDetail.Builder humidity(int humidity) {
            weather.humidity = humidity;
            return this;
        }

        public UIWeatherDetail.Builder pressure(float pressure) {
            weather.pressure = pressure;
            return this;
        }

        public UIWeatherDetail.Builder windSpeed(float windSpeed) {
            weather.windSpeed = windSpeed;
            return this;
        }

        public UIWeatherDetail.Builder windDegree(float windDegree) {
            weather.windDegree = windDegree;
            return this;
        }

        public UIWeatherDetail build() {
            return weather;
        }
    }
}
