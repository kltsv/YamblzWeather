package com.ivanantsiferov.yamblzweather;

import com.ivanantsiferov.yamblzweather.utils.DataUtil;
import com.ringov.yamblzweather.data.Converter;
import com.ringov.yamblzweather.data.db.data.DBWeather;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ConverterTest {

    @Test
    public void convertWeatherDbToUi() {
        DBWeather dbWeather = DataUtil.getDBWeather();

        UIWeather uiWeather = Converter.getUIWeather(dbWeather);
        assertEquals(dbWeather.getTemperature(), uiWeather.getTemperature());
        assertEquals(dbWeather.getTime(), uiWeather.getTime());
    }
}
