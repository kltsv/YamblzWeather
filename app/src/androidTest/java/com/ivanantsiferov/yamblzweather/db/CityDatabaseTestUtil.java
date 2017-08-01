package com.ivanantsiferov.yamblzweather.db;

import com.ringov.yamblzweather.data.db.city.DBCity;

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
        return new DBCity(0, "Moscow", 524901);
    }

    static DBCity Kathmandu() {
        return new DBCity(1, "Kathmandu", 1283240);
    }

    static DBCity Kiev() {
        return new DBCity(2, "Kiev", 703448);
    }
}
