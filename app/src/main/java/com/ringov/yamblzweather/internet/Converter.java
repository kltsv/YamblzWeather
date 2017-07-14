package com.ringov.yamblzweather.internet;

import com.ringov.yamblzweather.internet.content.WeatherResponse;
import com.ringov.yamblzweather.viewmodel.model.WeatherInfo;

/**
 * Created by ringov on 14.07.17.
 */

public class Converter {
    public static WeatherInfo getWeatherInfo(WeatherResponse response) {
        double temperature = ConvertUtils.kelvinToCelsius(response.getMain().getTemp());
        return new WeatherInfo.Builder()
                .temperature(temperature)
                .build();
    }

    private static class ConvertUtils {
        public static double kelvinToCelsius(double celsius) {
            return celsius - 273.15;
        }
    }
}
