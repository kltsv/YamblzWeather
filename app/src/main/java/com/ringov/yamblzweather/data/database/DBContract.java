package com.ringov.yamblzweather.data.database;

/**
 * Constants for AppDatabase
 */
public final class DBContract {

    private DBContract() {
    }

    public static final String DATABASE_NAME = "weather";

    public static final String TABLE_FORECAST = "forecast";
    public static final String TABLE_CITIES = "cities";
    public static final String TABLE_FAVORITE_CITIES = "favorite_cities";

    // Entities columns names
    public static final String COLUMN_ID = "_id";

    // City entity
    public static final String COLUMN_CITY_NAME = "city_name";
    public static final String COLUMN_CITY_ID = "city_id";

    // Favorite city entity
    public static final String COLUMN_ENABLED = "enabled";

    // Weather entity
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TEMP_MAX = "temperature_max";
    public static final String COLUMN_TEMP_MIN = "temperature_min";
    public static final String COLUMN_CONDITION = "condition";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DEGREE = "wind_degree";
}
