package com.ivanantsiferov.yamblzweather.room_database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.data.database.AppDatabase;
import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.dao.WeatherDAO;
import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;
import com.ringov.yamblzweather.data.database.entity.DBWeather;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest {

    private AppDatabase db;
    private CityDAO cityDAO;
    private FavoriteCityDAO favoriteCityDAO;
    private WeatherDAO weatherDAO;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        cityDAO = db.cityDAO();
        favoriteCityDAO = db.favoriteCityDAO();
        weatherDAO = db.weatherDAO();

        List<DBCity> citiesMock = CityTableTestUtil.getAll();
        cityDAO.insertAll(citiesMock);
        List<DBFavoriteCity> favoriteCitiesMock = FavoriteCityTableTestUtil.getAll();
        favoriteCityDAO.insertAll(favoriteCitiesMock);
        List<DBWeather> forecastMock = ForecastTableTestUtil.getAll();
        weatherDAO.insertAll(forecastMock);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void getCurrentWeatherForFavoriteCity() {
        DBFavoriteCity favoriteCity = favoriteCityDAO.getEnabled();
        DBWeather dbWeather = weatherDAO.getCurrentWeather(favoriteCity.getCity_id());
        assertEquals(favoriteCity.getCity_id(), dbWeather.getCityId());
    }

    @Test
    public void getForecastForFavoriteCity() {
        DBFavoriteCity favoriteCity = favoriteCityDAO.getEnabled();
        List<DBWeather> forecast =
                weatherDAO.getForecast(favoriteCity.getCity_id(), System.currentTimeMillis());
        assertTrue(forecast.size() > 0);
        assertEquals(favoriteCity.getCity_id(), forecast.get(0).getCityId());
    }

    @Test
    public void addCityToFavoritesAndEnableIt() {
        DBCity city = cityDAO.getById(CityTableTestUtil.Kiev().getCity_id());
        DBFavoriteCity currentFavorite = favoriteCityDAO.getEnabled();
        currentFavorite.setEnabled(false);
        favoriteCityDAO.insert(currentFavorite);

        DBFavoriteCity favoriteCity = new DBFavoriteCity(city.getCity_name(), city.getCity_id(), 1);
        favoriteCityDAO.insert(favoriteCity);

        DBFavoriteCity result = favoriteCityDAO.getEnabled();
        assertEquals(favoriteCity.getCity_id(), result.getCity_id());
    }
}
