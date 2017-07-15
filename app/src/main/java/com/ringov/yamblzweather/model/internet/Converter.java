package com.ringov.yamblzweather.model.internet;

import com.ringov.yamblzweather.model.db.data.DBWeather;
import com.ringov.yamblzweather.model.internet.data.Weather;
import com.ringov.yamblzweather.model.internet.data.ResponseWeather;
import com.ringov.yamblzweather.viewmodel.data.WeatherCondition;
import com.ringov.yamblzweather.viewmodel.data.WeatherInfo;

import java.util.List;

/**
 * Created by ringov on 14.07.17.
 */

public class Converter {
    public static WeatherInfo getWeatherInfo(ResponseWeather response) {
        double temperature = ConvertUtils.kelvinToCelsius(response.getMain().getTemp());
        List<Weather> weathers = response.getWeather();
        WeatherCondition weatherCondition = WeatherCondition.Other;
        if (weathers != null) {
            weatherCondition = ConvertUtils.weatherIdToCondition(weathers.get(0).getId());
        }
        return new WeatherInfo.Builder()
                .temperature(temperature)
                .weatherCondition(weatherCondition)
                .build();
    }

    public static WeatherInfo getWeatherInfo(DBWeather dbResponse) {
        return new WeatherInfo.Builder()
                .temperature(15.9)
                .weatherCondition(WeatherCondition.Other)
                .build();
    }

    private static class ConvertUtils {
        public static double kelvinToCelsius(double celsius) {
            return celsius - 273.15;
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
