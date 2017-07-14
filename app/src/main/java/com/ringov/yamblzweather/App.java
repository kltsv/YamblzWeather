package com.ringov.yamblzweather;

import android.app.Application;

import com.ringov.yamblzweather.di.about.AboutModule;
import com.ringov.yamblzweather.di.app.AppComponent;
import com.ringov.yamblzweather.di.app.AppModule;
import com.ringov.yamblzweather.di.app.DaggerAppComponent;
import com.ringov.yamblzweather.di.weather.WeatherModule;

/**
 * Created by ringov on 12.07.17.
 */

public class App extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .weatherModule(new WeatherModule())
                .build();
    }
}
