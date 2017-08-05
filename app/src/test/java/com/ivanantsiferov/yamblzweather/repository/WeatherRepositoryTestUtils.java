package com.ivanantsiferov.yamblzweather.repository;

import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.entity.ForecastResponse;
import com.ringov.yamblzweather.data.networking.entity.ResponseWeather;

import java.util.ArrayList;
import java.util.List;

final class WeatherRepositoryTestUtils {

    private WeatherRepositoryTestUtils() {
    }

    static DBWeather dbWeather() {
        return new DBWeather(0, 0, 0, 0, 0, 0, 0, 0);
    }

    static List<DBWeather> dbWeatherList(int size) {
        List<DBWeather> dbWeatherList = new ArrayList<>();

        for (int i = 0; i < size; i++)
            dbWeatherList.add(dbWeather());

        return dbWeatherList;
    }

    static ForecastResponse forecastResponse(int size) {
        ForecastResponse forecastResponse = new ForecastResponse();

        List<ResponseWeather> responseWeather = new ArrayList<>();
        for (int i = 0; i < size; i++)
            responseWeather.add(new ResponseWeather());

        forecastResponse.setForecast(responseWeather);

        return forecastResponse;
    }
}
