package com.ringov.yamblzweather.domain.weather;

import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    // TODO Complete methods

    private WeatherDAO weatherDAO;
    private FavoriteCityDAO favoriteCityDAO;
    private WeatherAPI weatherAPI;

    @Inject
    public WeatherRepositoryImpl(WeatherDAO weatherDAO, FavoriteCityDAO favoriteCityDAO, WeatherAPI weatherAPI) {
        this.weatherDAO = weatherDAO;
        this.favoriteCityDAO = favoriteCityDAO;
        this.weatherAPI = weatherAPI;
    }

    @Override
    public Single<List<UIWeatherList>> getForecast() {
        return null;
    }

    @Override
    public Single<UIWeatherDetail> getWeather(long time) {
        return null;
    }

    private Single<List<DBWeather>> getFromAPI(int cityId) {
        return weatherAPI.getForecast(cityId).map(Mapper::APItoDB);
    }

    private Single<List<DBWeather>> getFromCache(int cityId) {
        return Single.just(weatherDAO.getForecast(cityId, getCurrentTime()));
    }

    private int getCurrentCityId() {
        return favoriteCityDAO.getEnabled().getCity_id();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
