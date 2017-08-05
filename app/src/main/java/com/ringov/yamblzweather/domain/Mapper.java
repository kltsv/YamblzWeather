package com.ringov.yamblzweather.domain;

import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.entity.ForecastResponse;
import com.ringov.yamblzweather.data.networking.entity.ResponseWeather;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;
import com.ringov.yamblzweather.presentation.entity.WeatherCondition;

import java.util.ArrayList;
import java.util.List;

public final class Mapper {

    private Mapper() {
    }

    public static DBWeather APItoDB(ResponseWeather responseWeather, int cityId) {
        return new DBWeather(
                cityId,
                responseWeather.getDt(),
                responseWeather.getMain().getTemp(),
                responseWeather.getWeather().get(0).getId(),
                responseWeather.getMain().getHumidity(),
                responseWeather.getMain().getPressure(),
                responseWeather.getWind().getSpeed(),
                responseWeather.getWind().getDeg()
        );
    }

    public static List<DBWeather> APItoDB(ForecastResponse forecastResponse) {
        List<DBWeather> weather = new ArrayList<>();

        int cityId = forecastResponse.getCity().getId();
        for (ResponseWeather response : forecastResponse.getForecast())
            weather.add(APItoDB(response, cityId));

        return weather;
    }

    public static UIWeatherList DBtoUI(DBWeather weather) {
        return new UIWeatherList.Builder()
                .time(weather.getTime())
                .temperature(weather.getTemperature())
                .condition(getConditionById(weather.getCondition()))
                .build();
    }

    public static List<UIWeatherList> DBtoUI(List<DBWeather> weatherList) {
        List<UIWeatherList> converted = new ArrayList<>();

        for (DBWeather weather : weatherList)
            converted.add(DBtoUI(weather));

        return converted;
    }

    public static UIWeatherDetail DBWeather_to_UIWeatherDetail(DBWeather dbWeather) {
        return new UIWeatherDetail.Builder()
                .condition(getConditionById(dbWeather.getCondition()))
                .humidity(dbWeather.getHumidity())
                .pressure(dbWeather.getPressure())
                .temperature(dbWeather.getTemperature())
                .time(dbWeather.getTime())
                .windDegree(dbWeather.getWindDegree())
                .windSpeed(dbWeather.getWindSpeed())
                .build();
    }

    private static WeatherCondition getConditionById(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return WeatherCondition.Storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return WeatherCondition.LightRain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return WeatherCondition.Rain;
        } else if (weatherId == 511) {
            return WeatherCondition.Snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return WeatherCondition.Rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return WeatherCondition.Snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return WeatherCondition.Fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return WeatherCondition.Storm;
        } else if (weatherId == 800) {
            return WeatherCondition.Clear;
        } else if (weatherId == 801) {
            return WeatherCondition.LightClouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return WeatherCondition.Cloudy;
        } else {
            return WeatherCondition.Other;
        }
    }
}
