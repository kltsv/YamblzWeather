package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.presentation.ui.main.forecast.ForecastViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ForecastViewModelTest extends ViewModelTest {

    private ForecastViewModel forecastViewModel;

    @Before
    public void prepare() {
        when(weatherRepository.getForecast(anyBoolean()))
                .thenReturn(Single.just(new ArrayList<>()));
        when(favoriteCityRepository.getSelectedCityName()).thenReturn(Single.just("Moscow"));

        forecastViewModel =
                new ForecastViewModel(router, weatherRepository, favoriteCityRepository);
    }

    @Test
    public void swipeToRefresh() {
        forecastViewModel.onRefresh();
        verify(weatherRepository, times(1)).getForecast(true);
    }

    @Test
    public void openDetails() {
        forecastViewModel.openWeatherDetails(ViewModelTestUtils.uiWeatherList());
        verify(router, times(1)).execute(any());
    }
}
