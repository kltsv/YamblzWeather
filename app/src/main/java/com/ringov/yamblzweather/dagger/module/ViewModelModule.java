package com.ringov.yamblzweather.dagger.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ringov.yamblzweather.dagger.ViewModelKey;
import com.ringov.yamblzweather.presentation.base.CustomViewModelFactory;
import com.ringov.yamblzweather.presentation.ui.details.DetailsViewModel;
import com.ringov.yamblzweather.presentation.ui.main.MainViewModel;
import com.ringov.yamblzweather.presentation.ui.main.forecast.ForecastViewModel;
import com.ringov.yamblzweather.presentation.ui.add_city.AddCityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ForecastViewModel.class)
    abstract ViewModel bindWeatherViewModel(ForecastViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddCityViewModel.class)
    abstract ViewModel bindLocationViewModel(AddCityViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(DetailsViewModel viewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(CustomViewModelFactory factory);
}
