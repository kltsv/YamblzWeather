package com.ivanantsiferov.yamblzweather.room_database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.data.db.database.AppDatabase;
import com.ringov.yamblzweather.data.db.database.dao.CityDAO;
import com.ringov.yamblzweather.data.db.database.entity.DBCity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CityDatabaseTest {

    private CityDAO cityDAO;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        cityDAO = db.cityDAO();
        // Fill DB with data
        List<DBCity> citiesMock = CityDatabaseTestUtil.getAll();
        cityDAO.insertAll(citiesMock);
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void checkIfMockDataWrittenSuccessfully() {
        List<DBCity> citiesDb = cityDAO.getAll();
        assertEquals(CityDatabaseTestUtil.getAll().size(), citiesDb.size());
    }

    @Test
    public void checkEntities() {
        DBCity moscowMock = CityDatabaseTestUtil.Moscow();
        DBCity moscowDb = cityDAO.getById(moscowMock.getCity_id());
        assertEquals(moscowMock.getCity_name(), moscowDb.getCity_name());

        DBCity kathmanduMock = CityDatabaseTestUtil.Kathmandu();
        DBCity kathmanduDb = cityDAO.getByName(kathmanduMock.getCity_name());
        assertEquals(kathmanduMock.getCity_id(), kathmanduDb.getCity_id());
    }

    @Test
    public void suggestionsQuery() {
        List<DBCity> suggestions;

        DBCity moscowMock = CityDatabaseTestUtil.Moscow();
        suggestions = cityDAO.getSuggestions("mosc%", 1);
        assertEquals(moscowMock.getCity_name(), suggestions.get(0).getCity_name());

        DBCity kievMock = CityDatabaseTestUtil.Kiev();
        suggestions = cityDAO.getSuggestions("ki%", 1);
        assertEquals(kievMock.getCity_id(), suggestions.get(0).getCity_id());
    }
}
