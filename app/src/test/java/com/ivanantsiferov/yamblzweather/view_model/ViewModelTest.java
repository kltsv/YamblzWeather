package com.ivanantsiferov.yamblzweather.view_model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.ringov.yamblzweather.domain.repository.city_suggestions.CitySuggestionsRepository;
import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.navigation.base.Router;

import org.junit.Rule;

import static org.mockito.Mockito.mock;

public abstract class ViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    protected Router router = mock(Router.class);
    protected WeatherRepository weatherRepository = mock(WeatherRepository.class);
    protected CitySuggestionsRepository citySuggestionsRepository = mock(CitySuggestionsRepository.class);
    protected FavoriteCityRepository favoriteCityRepository = mock(FavoriteCityRepository.class);
}
