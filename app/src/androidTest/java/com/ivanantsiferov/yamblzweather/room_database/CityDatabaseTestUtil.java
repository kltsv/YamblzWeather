package com.ivanantsiferov.yamblzweather.room_database;

import com.ringov.yamblzweather.data.db.database.entity.DBCity;

import java.util.ArrayList;
import java.util.List;

final class CityDatabaseTestUtil {

    private CityDatabaseTestUtil() {}

    static List<DBCity> getAll() {
        ArrayList<DBCity> cities = new ArrayList<>();
        cities.add(Moscow());
        cities.add(Kathmandu());
        cities.add(Kiev());
        return cities;
    }

    static DBCity Moscow() {
        return new DBCity("Moscow", 524901);
    }

    static DBCity Kathmandu() {
        return new DBCity("Kathmandu", 1283240);
    }

    static DBCity Kiev() {
        return new DBCity("Kiev", 703448);
    }
}
