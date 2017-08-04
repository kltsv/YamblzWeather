package com.ivanantsiferov.yamblzweather.room_database;

import com.ringov.yamblzweather.data.database.entity.DBFavoriteCity;

import java.util.ArrayList;
import java.util.List;

public final class FavoriteCityTableTestUtil {

    private FavoriteCityTableTestUtil() {
    }

    static List<DBFavoriteCity> getAll() {
        ArrayList<DBFavoriteCity> cities = new ArrayList<>();
        cities.add(Moscow());
        cities.add(Tiraspol());
        cities.add(Ashwood());
        return cities;
    }

    static DBFavoriteCity Moscow() {
        return new DBFavoriteCity("Moscow", 524901, 1);
    }

    static DBFavoriteCity Tiraspol() {
        return new DBFavoriteCity("Tiraspol", 617239, 0);
    }

    static DBFavoriteCity Ashwood() {
        return new DBFavoriteCity("Ashwood", 7932611, 0);
    }
}
