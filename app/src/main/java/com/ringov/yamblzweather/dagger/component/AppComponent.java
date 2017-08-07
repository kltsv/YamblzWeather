package com.ringov.yamblzweather.dagger.component;

import android.app.Application;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.dagger.module.ApplicationModule;
import com.ringov.yamblzweather.dagger.module.DatabaseModule;
import com.ringov.yamblzweather.dagger.module.ActivityModule;
import com.ringov.yamblzweather.dagger.module.NavigationModule;
import com.ringov.yamblzweather.dagger.module.NetworkModule;
import com.ringov.yamblzweather.dagger.module.RepositoryModule;
import com.ringov.yamblzweather.dagger.module.RxSchedulerModule;
import com.ringov.yamblzweather.dagger.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        NavigationModule.class,
        ActivityModule.class,
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
}
