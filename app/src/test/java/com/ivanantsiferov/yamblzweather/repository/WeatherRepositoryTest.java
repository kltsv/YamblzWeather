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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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
                .thenReturn(Single.just(RepositoryTestUtils.forecastResponse(DAYS_COUNT)));

        when(favoriteCityDAO.getEnabled()).thenReturn(RepositoryTestUtils.enabledCity());

        when(weatherDAO.getForecast(CITY_ID))
                .thenReturn(RepositoryTestUtils.dbWeatherList(DAYS_COUNT));

        when(weatherDAO.getByCityId(CITY_ID))
                .thenReturn(RepositoryTestUtils.dbWeatherList(0));

        when(weatherDAO.getByTime(anyLong()))
                .thenReturn(RepositoryTestUtils.dbWeatherList(0));

        when(weatherDAO.getWeather(anyInt(), anyLong()))
                .thenReturn(RepositoryTestUtils.dbWeather());
    }

    @Test
    public void getWeather() {
        weatherRepository.getWeather(0L).test()
                .assertValue(uiWeatherDetail -> uiWeatherDetail != null)
                .assertComplete();
        verify(weatherDAO, times(1)).getWeather(anyInt(), anyLong());
    }

    @Test
    public void getForecastFromCache() {
        weatherRepository.getForecast(false).test()
                .assertValue(uiWeatherLists -> uiWeatherLists.size() == DAYS_COUNT)
                .assertComplete();
        verify(weatherDAO, times(1)).getForecast(anyInt());
        verify(favoriteCityDAO, times(1)).getEnabled();
        verify(weatherAPI, never()).getDailyForecast(anyInt(), anyInt());
    }

    @Test
    public void getForecastFromAPI() {
        weatherRepository.getForecast(true).test()
                .assertValue(uiWeatherLists -> uiWeatherLists.size() == DAYS_COUNT)
                .assertComplete();
        verify(favoriteCityDAO, times(2)).getEnabled();
        verify(weatherAPI, times(1)).getDailyForecast(anyInt(), anyInt());
        verify(weatherDAO, times(1)).getByCityId(anyInt());
        verify(weatherDAO, times(1)).deleteAll(anyList());
        verify(weatherDAO, times(1)).insertAll(anyList());
    }
}
