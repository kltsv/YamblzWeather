package com.ringov.yamblzweather.dagger.component;

import com.ringov.yamblzweather.dagger.module.ApplicationModule;
import com.ringov.yamblzweather.dagger.module.DatabaseModule;
import com.ringov.yamblzweather.dagger.module.NetworkModule;
import com.ringov.yamblzweather.dagger.module.RepositoryModule;
import com.ringov.yamblzweather.dagger.module.RxSchedulerModule;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationViewModel;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class,
        NetworkModule.class,
        RxSchedulerModule.class,
        RepositoryModule.class
})
@Singleton
public interface AppComponent {

    void inject(WeatherViewModel weatherViewModel);

    void inject(LocationViewModel locationViewModel);
}
