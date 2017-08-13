package com.ivanantsiferov.yamblzweather.room_database;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.data.database.AppDatabase;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class FavoriteCityTableTest {

    // Because of RxJava
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FavoriteCityDAO favoriteCityDAO;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
        favoriteCityDAO = db.favoriteCityDAO();
        List<DBFavoriteCity> citiesMock = FavoriteCityTableTestUtil.getAll();
        favoriteCityDAO.insertAll(citiesMock);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void checkAll() throws NullPointerException {
        favoriteCityDAO
                .getAll()
                .test()
                .assertValue(result -> result.size() == 3);
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

    @Test
    public void deleteFirst() {
        DBFavoriteCity city = favoriteCityDAO.getFirst();
        favoriteCityDAO.delete(city);
        assertNull(favoriteCityDAO.getById(city.getCity_id()));

        favoriteCityDAO.insert(city);
        assertNotNull(favoriteCityDAO.getByName(city.getCity_name()));
    }
}
