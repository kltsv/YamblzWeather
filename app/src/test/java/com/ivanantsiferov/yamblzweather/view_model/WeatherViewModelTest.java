package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherViewModel;

import static org.mockito.Mockito.mock;

import org.junit.Before;

public class WeatherViewModelTest {

    // TODO complete this test when WeatherViewModel will be completed

    private WeatherViewModel viewModel;

    @Before
    public void prepare() {
        Router router = mock(Router.class);
        WeatherRepository weatherRepository = mock(WeatherRepository.class);

        viewModel = new WeatherViewModel(router, weatherRepository);
    }
}
