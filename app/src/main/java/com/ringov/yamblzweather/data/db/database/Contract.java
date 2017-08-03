package com.ringov.yamblzweather.data.db.database;

/**
 * Constants for AppDatabase
 */
public final class Contract {

    private Contract() {
    }

    public static final String DATABASE_NAME = "weather";

    public static final String TABLE_FORECAST = "forecast";
    public static final String TABLE_CITIES = "cities";

    // Entities columns names
    public static final String COLUMN_ID = "_id";

    // City entity
    public static final String COLUMN_CITY_NAME = "city_name";
    public static final String COLUMN_CITY_ID = "city_id";

    // Weather entity
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TEMPERATURE = "temperature";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DEGREE = "wind_degree";
}
