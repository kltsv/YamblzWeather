package com.ringov.yamblzweather.di.app;

import com.ringov.yamblzweather.navigation.base.Router;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan on 26.07.2017.
 */

@Module
public class NavigationModule {

    private Router router;

    public NavigationModule(Router router) {
        this.router = router;
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return router;
    }
}
