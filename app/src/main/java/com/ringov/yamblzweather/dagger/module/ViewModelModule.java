package com.ringov.yamblzweather.dagger.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ringov.yamblzweather.dagger.ViewModelKey;
import com.ringov.yamblzweather.presentation.base.CustomViewModelFactory;
import com.ringov.yamblzweather.presentation.ui.details.DetailsViewModel;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationViewModel;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherViewModel;
import com.ringov.yamblzweather.presentation.ui.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    abstract ViewModel bindWeatherViewModel(WeatherViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel.class)
    abstract ViewModel bindLocationViewModel(LocationViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(DetailsViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(CustomViewModelFactory factory);
}
