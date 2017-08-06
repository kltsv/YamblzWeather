package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.presentation.ui.details.DetailsFragment;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationFragment;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract WeatherFragment contributeWeatherFragment();

    @ContributesAndroidInjector
    abstract LocationFragment contributeLocationFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment contributeDetailsFragment();
}
