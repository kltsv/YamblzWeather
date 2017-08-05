package com.ivanantsiferov.yamblzweather.repository;

import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.domain.exceptions.EmptyForecastException;
import com.ringov.yamblzweather.domain.exceptions.NoInternetConnectionException;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepositoryImpl;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherRepositoryTest {

    private final static int CITY_ID = 524901;

    private WeatherRepository weatherRepository;

    private Scheduler schedulerUI;
    private Scheduler schedulerIO;
    private Scheduler schedulerComputation;

    private WeatherDAO weatherDAO;
    private FavoriteCityDAO favoriteCityDAO;
    private WeatherAPI weatherAPI;

    @Before
    public void prepare() {
        schedulerUI = new TestScheduler();
        schedulerIO = new TestScheduler();
        schedulerComputation = new TestScheduler();

        weatherDAO = mock(WeatherDAO.class);
        favoriteCityDAO = mock(FavoriteCityDAO.class);
        weatherAPI = mock(WeatherAPI.class);

        weatherRepository = new WeatherRepositoryImpl(
                schedulerUI, schedulerComputation, schedulerIO,
                weatherDAO, favoriteCityDAO, weatherAPI
        );

        when(weatherDAO.getWeather(CITY_ID, System.currentTimeMillis()))
                .thenReturn(WeatherRepositoryTestUtils.dbWeather());
        when(weatherDAO.getForecast(CITY_ID, System.currentTimeMillis()))
                .thenReturn(WeatherRepositoryTestUtils.dbWeatherList(7));
        when(weatherDAO.getByCiyId(CITY_ID))
                .thenReturn(WeatherRepositoryTestUtils.dbWeatherList(1));

        when(favoriteCityDAO.getEnabled())
                .thenReturn(new DBFavoriteCity("Moscow", CITY_ID, 1));

        when(weatherAPI.getForecast(CITY_ID))
                .thenReturn(Single.just(WeatherRepositoryTestUtils.forecastResponse(7)));
    }

    @Test
    public void getForecastCommon() {
        weatherRepository.getForecast()
                .subscribe(uiWeatherLists -> assertTrue(!uiWeatherLists.isEmpty()));
    }

    @Test
    public void getForecastWithoutInternet() {
        when(weatherAPI.getForecast(CITY_ID))
                .thenReturn(Single.error(new NoInternetConnectionException()));

        weatherRepository.getForecast()
                .subscribe((uiWeatherLists, throwable) -> {
                    assertTrue(throwable instanceof NoInternetConnectionException);
                });
    }

    @Test
    public void tryToCacheEmptyResponse() {
        when(weatherAPI.getForecast(CITY_ID))
                .thenReturn(Single.just(WeatherRepositoryTestUtils.forecastResponse(0)));

        weatherRepository.getForecast()
                .subscribe((uiWeatherLists, throwable) -> {
                    assertTrue(throwable instanceof EmptyForecastException);
                });
    }
}
