package com.ringov.yamblzweather.model;

import com.ringov.yamblzweather.model.db.data.DBWeather;
import com.ringov.yamblzweather.model.networking.data.Weather;
import com.ringov.yamblzweather.model.networking.data.ResponseWeather;
import com.ringov.yamblzweather.presentation.data.WeatherCondition;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import java.util.List;

public class Converter {

    public static DBWeather getDBWeather(ResponseWeather response) {
        float temperature = ConvertUtils.kelvinToCelsius(response.getMain().getTemp());
        List<Weather> weathers = response.getWeather();
        int conditionId = 0;
        if (weathers != null) {
            conditionId = weathers.get(0).getId();
        }
        return new DBWeather.Builder(System.currentTimeMillis())
                .temperature(temperature)
                .weatherCondition(conditionId)
                .build();
    }

    public static UIWeather getUIWeather(DBWeather dbResponse) {
        return new UIWeather.Builder()
                .time(dbResponse.getTime())
                .temperature(dbResponse.getTemperature())
                .weatherCondition(ConvertUtils.weatherIdToCondition(dbResponse.getConditionId()))
                .build();
    }

    private static class ConvertUtils {
        public static float kelvinToCelsius(float celsius) {
            return celsius - 273.15f;
        }

        private static WeatherCondition weatherIdToCondition(int id) {
            if (id >= 200 && id < 300) {
                return WeatherCondition.Thunderstorm;
            } else if (id >= 300 && id < 600) {
                return WeatherCondition.Rainy;
            } else if (id >= 600 && id < 700) {
                return WeatherCondition.Snow;
            } else if (id >= 700 && id < 800) {
                return WeatherCondition.Atmospherically;
            } else if (id == 800) {
                return WeatherCondition.Clear;
            } else if (id > 800 && id < 900) {
                return WeatherCondition.Cloudy;
            } else if (id >= 900 && id < 950) {
                return WeatherCondition.Extreme;
            } else if (id >= 957 && id <= 962) {
                return WeatherCondition.Windy;
            } else if (id >= 951 && id <= 956) {
                return WeatherCondition.Calm;
            } else {
                return WeatherCondition.Other;
            }
        }
    }
}
