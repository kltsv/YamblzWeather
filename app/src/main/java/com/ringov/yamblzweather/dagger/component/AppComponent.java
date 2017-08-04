package com.ringov.yamblzweather.dagger.component;

import com.ringov.yamblzweather.dagger.module.ApplicationModule;
import com.ringov.yamblzweather.dagger.module.DatabaseModule;
import com.ringov.yamblzweather.dagger.module.LocationModule;
import com.ringov.yamblzweather.dagger.module.NetworkModule;
import com.ringov.yamblzweather.dagger.module.SettingsModule;
import com.ringov.yamblzweather.dagger.module.WeatherModule;
import com.ringov.yamblzweather.data.background_service.WeatherUpdateJob;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationViewModel;
import com.ringov.yamblzweather.presentation.ui.main.settings.SettingsFragment;
import com.ringov.yamblzweather.presentation.ui.main.about.AboutViewModel;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherViewModel;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        DatabaseModule.class,
        NetworkModule.class,
        WeatherModule.class,
        SettingsModule.class,
        LocationModule.class
})
@Singleton
public interface AppComponent {

    void inject(WeatherViewModel weatherViewModel);

    void inject(AboutViewModel aboutViewModel);

    void inject(LocationViewModel locationViewModel);

    void inject(WeatherUpdateJob job);

    void inject(SettingsFragment settingsFragment);
}
