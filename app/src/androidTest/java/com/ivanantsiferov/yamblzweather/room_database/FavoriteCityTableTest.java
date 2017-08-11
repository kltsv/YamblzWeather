package com.ivanantsiferov.yamblzweather.room_database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.data.database.AppDatabase;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class FavoriteCityTableTest {

    private FavoriteCityDAO favoriteCityDAO;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        favoriteCityDAO = db.favoriteCityDAO();
        List<DBFavoriteCity> citiesMock = FavoriteCityTableTestUtil.getAll();
        favoriteCityDAO.insertAll(citiesMock);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void checkEnabled() {
        DBFavoriteCity moscowMock = FavoriteCityTableTestUtil.Moscow();
        DBFavoriteCity moscowDb = favoriteCityDAO.getEnabled();
        assertEquals(moscowMock.getCity_id(), moscowDb.getCity_id());
    }

    @Test
    public void changeEnabled() {
        DBFavoriteCity currentEnabled = favoriteCityDAO.getEnabled();
        currentEnabled.setEnabled(false);
        favoriteCityDAO.insert(currentEnabled);

        DBFavoriteCity newEnabled = FavoriteCityTableTestUtil.Ashwood();
        newEnabled.setEnabled(true);
        favoriteCityDAO.insert(newEnabled);

        DBFavoriteCity resultEnabled = favoriteCityDAO.getEnabled();
        assertEquals(newEnabled.getCity_id(), resultEnabled.getCity_id());
    }
}
