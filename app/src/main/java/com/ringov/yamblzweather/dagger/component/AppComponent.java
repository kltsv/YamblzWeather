package com.ringov.yamblzweather.dagger.component;

import android.app.Application;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.dagger.module.ApplicationModule;
import com.ringov.yamblzweather.dagger.module.DatabaseModule;
import com.ringov.yamblzweather.dagger.module.MainActivityModule;
import com.ringov.yamblzweather.dagger.module.NetworkModule;
import com.ringov.yamblzweather.dagger.module.RepositoryModule;
import com.ringov.yamblzweather.dagger.module.RxSchedulerModule;
import com.ringov.yamblzweather.dagger.module.ViewModelModule;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationViewModel;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherViewModel;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        MainActivityModule.class,
        ApplicationModule.class,
        DatabaseModule.class,
        NetworkModule.class,
        RxSchedulerModule.class,
        RepositoryModule.class,
        ViewModelModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(App app);

    void inject(WeatherViewModel weatherViewModel);

    void inject(LocationViewModel locationViewModel);
}
