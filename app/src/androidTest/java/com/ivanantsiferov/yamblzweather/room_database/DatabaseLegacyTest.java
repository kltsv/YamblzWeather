package com.ivanantsiferov.yamblzweather.room_database;

import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.data.db.DatabaseLegacy;
import com.ringov.yamblzweather.data.db.data.DBWeather;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseLegacyTest {

    @Test
    public void saveLoadWeather() {
        DBWeather expected = new DBWeather
                .Builder(System.currentTimeMillis())
                .temperature(25f)
                .weatherCondition(200)
                .build();

        DatabaseLegacy.getInstance().saveWeather(expected);
        DBWeather fromDb = DatabaseLegacy.getInstance().loadWeather();
        assertEquals(expected.getConditionId(), fromDb.getConditionId());
        assertEquals(expected.getTime(), fromDb.getTime());
        assertEquals(expected.getTemperature(), fromDb.getTemperature());
    }

    @Test
    public void putGetCityId() {
        int expected = 524901;
        DatabaseLegacy.getInstance().putUserCity(expected);
        int cityId = DatabaseLegacy.getInstance().getUserCityId();
        assertEquals(expected, cityId);
    }
}
