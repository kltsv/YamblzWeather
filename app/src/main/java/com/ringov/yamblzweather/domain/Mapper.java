package com.ringov.yamblzweather.domain;

import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.entity.ForecastResponse;
import com.ringov.yamblzweather.data.networking.entity.ResponseWeather;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;
import com.ringov.yamblzweather.presentation.entity.WeatherCondition;
import com.ringov.yamblzweather.presentation.entity.WindDirection;

import java.util.ArrayList;
import java.util.List;

public final class Mapper {

    private Mapper() {
    }

    private static DBWeather APItoDB(ResponseWeather responseWeather, int cityId) {
        return new DBWeather(
                cityId,
                responseWeather.getDt(),
                responseWeather.getTemp().getMax(),
                responseWeather.getTemp().getMin(),
                responseWeather.getWeather().get(0).getId(),
                responseWeather.getHumidity(),
                responseWeather.getPressure(),
                responseWeather.getSpeed(),
                responseWeather.getDeg()
        );
    }

    public static List<DBWeather> APItoDB(ForecastResponse forecastResponse) {
        List<DBWeather> weather = new ArrayList<>();

        int cityId = forecastResponse.getCity().getId();
        for (ResponseWeather response : forecastResponse.getForecast())
            weather.add(APItoDB(response, cityId));

        return weather;
    }

    private static UIWeatherList DBtoUI(DBWeather weather) {
        return new UIWeatherList.Builder()
                .time(weather.getTime())
                .tempMax(weather.getTempMax())
                .tempMin(weather.getTempMin())
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
                .tempMin(dbWeather.getTempMin())
                .tempMax(dbWeather.getTempMax())
                .time(dbWeather.getTime())
                .windDirection(getWindDirectionByDegrees(dbWeather.getWindDegree()))
                .windSpeed(dbWeather.getWindSpeed())
                .build();
    }

    // Enum converters
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

    private static WindDirection getWindDirectionByDegrees(float degrees) {
        if (degrees >= 337.5 || degrees < 22.5) {
            return WindDirection.North;
        } else if (degrees >= 22.5 && degrees < 67.5) {
            return WindDirection.NorthEast;
        } else if (degrees >= 67.5 && degrees < 112.5) {
            return WindDirection.East;
        } else if (degrees >= 112.5 && degrees < 157.5) {
            return WindDirection.SouthEast;
        } else if (degrees >= 157.5 && degrees < 202.5) {
            return WindDirection.South;
        } else if (degrees >= 202.5 && degrees < 247.5) {
            return WindDirection.SouthWest;
        } else if (degrees >= 247.5 && degrees < 292.5) {
            return WindDirection.West;
        } else if (degrees >= 292.5 && degrees < 337.5) {
            return WindDirection.NorthWest;
        } else {
            throw new IllegalArgumentException("Trying to get wind direction with wrong arguments");
        }
    }
}
