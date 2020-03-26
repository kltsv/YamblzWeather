package com.ringov.yamblzweather.dagger.module;

import com.ringov.yamblzweather.navigation.RouterHolder;
import com.ringov.yamblzweather.navigation.base.NavigatorBinder;
import com.ringov.yamblzweather.navigation.base.Router;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class NavigationModule {

    private RouterHolder routerHolder = new RouterHolder();

    @Provides
    @Singleton
    Router provideRouter() {
        return routerHolder;
    }

    @Provides
    @Singleton
    NavigatorBinder provideNavigatorBinder() {
        return routerHolder;
    }
}
