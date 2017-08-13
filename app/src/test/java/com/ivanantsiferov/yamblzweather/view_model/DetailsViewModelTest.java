package com.ivanantsiferov.yamblzweather.view_model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.ringov.yamblzweather.domain.exceptions.NoInternetConnectionException;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.ui.details.DetailsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailsViewModelTest extends ViewModelTest {

    private DetailsViewModel detailsViewModel;

    @Before
    public void prepare() {
        detailsViewModel = new DetailsViewModel(weatherRepository);
    }

    @Test
    public void getWeather() {
        final long time = 123456;
        when(weatherRepository.getWeather(time))
                .thenReturn(Single.just(new UIWeatherDetail()));

        detailsViewModel.showWeatherFor(time);
        verify(weatherRepository, times(1)).getWeather(time);
    }

    @Test
    public void getError() {
        final long time = 123456;
        when(weatherRepository.getWeather(time))
                .thenReturn(Single.error(new NoInternetConnectionException()));

        detailsViewModel.showWeatherFor(time);
        verify(weatherRepository, times(1)).getWeather(time);
    }
}
