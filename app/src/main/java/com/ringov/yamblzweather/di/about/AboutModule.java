package com.ringov.yamblzweather.di.about;

import android.support.annotation.NonNull;

import com.ringov.yamblzweather.model.about.AboutRepository;
import com.ringov.yamblzweather.model.about.AboutRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ringov on 12.07.17.
 */

@Module
public class AboutModule {

    @Provides
    @NonNull
    @Singleton
    public AboutRepository provideAboutRepository() {
        return new AboutRepositoryImpl();
    }
}
