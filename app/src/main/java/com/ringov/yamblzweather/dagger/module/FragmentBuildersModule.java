package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.presentation.ui.main.add_city.AddCityFragment;
import com.ringov.yamblzweather.presentation.ui.details.DetailsFragment;
import com.ringov.yamblzweather.presentation.ui.main.forecast.ForecastFragment;
import com.ringov.yamblzweather.presentation.ui.main.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ForecastFragment contributeWeatherFragment();

    @ContributesAndroidInjector
    abstract AddCityFragment contributeLocationFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment contributeDetailsFragment();

    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();
}
