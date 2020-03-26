package com.ivanantsiferov.yamblzweather.repository;

import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;
import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.entity.City;
import com.ringov.yamblzweather.data.networking.entity.ForecastResponse;
import com.ringov.yamblzweather.data.networking.entity.ResponseWeather;
import com.ringov.yamblzweather.data.networking.entity.Temp;
import com.ringov.yamblzweather.data.networking.entity.Weather;

import java.util.ArrayList;
import java.util.List;

class RepositoryTestUtils {

    private RepositoryTestUtils() {
    }

    static List<DBCity> suggestions() {
        List<DBCity> sug = new ArrayList<>();
        sug.add(new DBCity("Moscow", 0));
        return sug;
    }

    static DBFavoriteCity enabledCity() {
        return new DBFavoriteCity("Moscow", 524901, 1);
    }

    static DBCity dbCityKathmandu() {
        return new DBCity("Kathmandu", 1283240);
    }

    static DBFavoriteCity favoriteCity() {
        return new DBFavoriteCity("Kiev", 123456, 0);
    }

    static List<DBFavoriteCity> dbFavoriteCityList() {
        List<DBFavoriteCity> cities = new ArrayList<>();
        cities.add(favoriteCity());
        cities.add(enabledCity());
        return cities;
    }

    static DBWeather dbWeather() {
        return new DBWeather(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    static List<DBWeather> dbWeatherList(int size) {
        List<DBWeather> dbWeatherList = new ArrayList<>();

        for (int i = 0; i < size; i++)
            dbWeatherList.add(dbWeather());

        return dbWeatherList;
    }

    static ForecastResponse forecastResponse(int size) {
        ForecastResponse forecastResponse = new ForecastResponse();
        City city = new City();
        city.setId(0);
        forecastResponse.setCity(city);

        ResponseWeather resp = new ResponseWeather();
        resp.setClouds(0);
        resp.setDeg(0);
        resp.setDt(0);
        resp.setHumidity(0);
        resp.setPressure(0f);
        resp.setSpeed(0f);
        Temp temp = new Temp();
        temp.setDay(0f);
        temp.setEve(0f);
        temp.setMax(0f);
        temp.setDay(0f);
        temp.setMorn(0f);
        temp.setNight(0f);
        resp.setTemp(temp);
        List<Weather> whatever = new ArrayList<>();
        Weather ohhMy = new Weather();
        ohhMy.setId(0);
        whatever.add(ohhMy);
        resp.setWeather(whatever);

        List<ResponseWeather> responseWeather = new ArrayList<>();
        for (int i = 0; i < size; i++)
            responseWeather.add(resp);

        forecastResponse.setForecast(responseWeather);

        return forecastResponse;
    }
}
