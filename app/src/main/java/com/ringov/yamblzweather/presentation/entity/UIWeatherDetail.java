package com.ringov.yamblzweather.presentation.entity;

public class UIWeatherDetail {

    private WeatherCondition condition;
    private float tempMax;
    private float tempMin;
    private long time;
    private int humidity;
    private float pressure;
    private float windSpeed;
    private float windDegree;

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

        public Builder humidity(int humidity) {
            weather.humidity = humidity;
            return this;
        }

        public Builder pressure(float pressure) {
            weather.pressure = pressure;
            return this;
        }

        public Builder windSpeed(float windSpeed) {
            weather.windSpeed = windSpeed;
            return this;
        }

        public Builder windDegree(float windDegree) {
            weather.windDegree = windDegree;
            return this;
        }

        public UIWeatherDetail build() {
            return weather;
        }
    }
}
