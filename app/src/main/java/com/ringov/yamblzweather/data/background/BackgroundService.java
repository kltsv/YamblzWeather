package com.ringov.yamblzweather.data.background;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.dagger.AppInjector;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.database.entity.DBWeather;
import com.ringov.yamblzweather.data.networking.WeatherAPI;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.ui.splash.SplashActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;

public class BackgroundService extends IntentService implements HasServiceInjector {

    private static final String SERVICE_NAME = "YamblzWeatherService";
    private static final int FORECAST_NOTIFICATION_ID = 5739;

    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }

    @Inject
    WeatherDAO weatherDAO;

    @Inject
    FavoriteCityDAO favoriteCityDAO;

    @Inject
    WeatherAPI weatherAPI;

    @Inject
    public BackgroundService() {
        super(SERVICE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppInjector.init((App) getApplication());
        AndroidInjection.inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int currentCityId = favoriteCityDAO.getEnabled().getCity_id();
        weatherAPI.getDailyForecast(currentCityId)
                .map(Mapper::APItoDB)
                .subscribe(forecastResponse -> {
                    // Delete old cache and save fetched data
                    List<DBWeather> oldCache = weatherDAO.getByCiyId(currentCityId);
                    weatherDAO.deleteAll(oldCache);
                    weatherDAO.insertAll(forecastResponse);

                    showPushNotification(
                            Mapper.DBWeather_to_UIWeatherDetail(forecastResponse.get(0)));
                });

        AlarmReceiver.completeWakefulIntent(intent);
    }

    private void showPushNotification(UIWeatherDetail weather) {
        String content = getApplicationContext()
                .getString(R.string.push_content, weather.getTempMax());
        String title = getApplicationContext()
                .getString(weather.getCondition().getFriendlyName());
        int icon = weather.getCondition().getConditionImage();
        int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setColor(color)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(content);

        Intent resultIntent = new Intent(this, SplashActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(FORECAST_NOTIFICATION_ID, builder.build());
    }
}
