package com.ringov.yamblzweather.di.app;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ringov on 12.07.17.
 */

@Module
public class AppModule {

    private Context appContext;

    public AppModule(@NonNull Context context) {
        this.appContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }
}
