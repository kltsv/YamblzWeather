package com.ringov.yamblzweather.model.db.data;

/**
 * Created by ringov on 15.07.17.
 */

public class DBWeather {
    private long time;
    private double temperature;
    private int condition;

    public DBWeather(long time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getConditionId() {
        return condition;
    }

    public long getTime() {
        return time;
    }

    public static class Builder {

        private DBWeather weather;

        public Builder(long time) {
            weather = new DBWeather(time);
        }

        public Builder temperature(double t) {
            weather.temperature = t;
            return this;
        }

        public Builder weatherCondition(int condition) {
            weather.condition = condition;
            return this;
        }

        public DBWeather build() {
            return weather;
        }
    }
}
