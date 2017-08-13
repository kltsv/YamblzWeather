package com.ivanantsiferov.yamblzweather.repository;

import com.ivanantsiferov.yamblzweather.CurrentThreadExecutor;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepositoryImpl;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherRepositoryTest {

    private final static int CITY_ID = 524901;
    private final static int DAYS_COUNT = 16;

    private WeatherRepository weatherRepository;

    private WeatherDAO weatherDAO;
    private FavoriteCityDAO favoriteCityDAO;
    private WeatherAPI weatherAPI;

    @Before
    public void prepare() {
        Scheduler scheduler = Schedulers.from(new CurrentThreadExecutor());

        weatherDAO = mock(WeatherDAO.class);
        favoriteCityDAO = mock(FavoriteCityDAO.class);
        weatherAPI = mock(WeatherAPI.class);

        weatherRepository = new WeatherRepositoryImpl(
                scheduler, scheduler, scheduler,
                weatherDAO, favoriteCityDAO, weatherAPI
        );

        when(weatherAPI.getDailyForecast(CITY_ID, DAYS_COUNT))
                .thenReturn(Single.just(RepositoryTestUtil.forecastResponse(DAYS_COUNT)));

        when(favoriteCityDAO.getEnabled()).thenReturn(RepositoryTestUtil.enabledCity());

        when(weatherDAO.getForecast(CITY_ID))
                .thenReturn(RepositoryTestUtil.dbWeatherList(DAYS_COUNT));

        when(weatherDAO.getByCityId(CITY_ID))
                .thenReturn(RepositoryTestUtil.dbWeatherList(0));

        when(weatherDAO.getByTime(anyLong()))
                .thenReturn(RepositoryTestUtil.dbWeatherList(0));

        when(weatherDAO.getWeather(anyInt(), anyLong()))
                .thenReturn(RepositoryTestUtil.dbWeather());
    }

    @Test
    public void getWeather() {
        weatherRepository.getWeather(0L).test()
                .assertValue(uiWeatherDetail -> uiWeatherDetail != null)
                .assertComplete();
        verify(weatherDAO, atLeastOnce()).getWeather(anyInt(), anyLong());
    }

    @Test
    public void getForecastFromCache() {
        weatherRepository.getForecast(false).test()
                .assertValue(uiWeatherLists -> uiWeatherLists.size() == DAYS_COUNT)
                .assertComplete();
        verify(weatherDAO, atLeastOnce()).getForecast(anyInt());
        verify(favoriteCityDAO, atLeastOnce()).getEnabled();
        verify(weatherAPI, never()).getDailyForecast(anyInt(), anyInt());
    }

    @Test
    public void getForecastFromAPI() {
        weatherRepository.getForecast(true).test()
                .assertValue(uiWeatherLists -> uiWeatherLists.size() == DAYS_COUNT)
                .assertComplete();
        verify(favoriteCityDAO, atLeastOnce()).getEnabled();
        verify(weatherAPI, atLeastOnce()).getDailyForecast(anyInt(), anyInt());
        verify(weatherDAO, atLeastOnce()).getByCityId(anyInt());
        verify(weatherDAO, atLeastOnce()).deleteAll(anyList());
        verify(weatherDAO, atLeastOnce()).insertAll(anyList());
    }
}
