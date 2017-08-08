package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.presentation.ui.details.DetailsFragment;
import com.ringov.yamblzweather.presentation.ui.main.forecast.ForecastFragment;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ForecastFragment contributeWeatherFragment();

    @ContributesAndroidInjector
    abstract LocationFragment contributeLocationFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment contributeDetailsFragment();
}
