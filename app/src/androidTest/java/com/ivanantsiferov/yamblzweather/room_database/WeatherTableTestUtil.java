package com.ivanantsiferov.yamblzweather.room_database;

import com.ringov.yamblzweather.data.database.entity.DBWeather;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

final class WeatherTableTestUtil {

    private WeatherTableTestUtil() {
    }

    static List<DBWeather> getAll() {
        ArrayList<DBWeather> forecast = new ArrayList<>();
        forecast.add(MoscowCurrent());
        forecast.add(MoscowTomorrow());
        forecast.add(KievCurrent());
        return forecast;
    }

    static DBWeather MoscowCurrent() {
        return new DBWeather(
                524901,
                System.currentTimeMillis(),
                25.5f,
                800,
                76,
                700,
                3.4f,
                234.8f,
                20f
        );
    }

    static DBWeather MoscowTomorrow() {
        return new DBWeather(
                524901,
                System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1),
                27.5f,
                800,
                78,
                756,
                3.9f,
                135.2f,
                34f
        );
    }

    static DBWeather KievCurrent() {
        return new DBWeather(
                703448,
                System.currentTimeMillis(),
                20.2f,
                800,
                71,
                734,
                6.1f,
                178.4f,
                90f
        );
    }
}
