package com.ivanantsiferov.yamblzweather.db;

import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.data.db.Database;
import com.ringov.yamblzweather.data.db.data.DBWeather;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    @Test
    public void saveLoadWeather() {
        DBWeather expected = new DBWeather
                .Builder(System.currentTimeMillis())
                .temperature(25f)
                .weatherCondition(200)
                .build();

        Database.getInstance().saveWeather(expected);
        DBWeather fromDb = Database.getInstance().loadWeather();
        assertEquals(expected.getConditionId(), fromDb.getConditionId());
        assertEquals(expected.getTime(), fromDb.getTime());
        assertEquals(expected.getTemperature(), fromDb.getTemperature());
    }

    @Test
    public void putGetCityId() {
        int expected = 524901;
        Database.getInstance().putUserCity(expected);
        int cityId = Database.getInstance().getUserCityId();
        assertEquals(expected, cityId);
    }
}
