package com.ringov.yamblzweather.di.app;

import com.ringov.yamblzweather.di.about.AboutModule;
import com.ringov.yamblzweather.di.weather.WeatherModule;
import com.ringov.yamblzweather.viewmodel.about.AboutViewModel;
import com.ringov.yamblzweather.viewmodel.weather.WeatherViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ringov on 12.07.17.
 */

@Component(modules = {WeatherModule.class, AboutModule.class})
@Singleton
public interface AppComponent {
    void inject(WeatherViewModel weatherViewModel);

    void inject(AboutViewModel aboutViewModel);
}
