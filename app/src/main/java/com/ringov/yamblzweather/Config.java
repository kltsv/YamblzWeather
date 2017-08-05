package com.ringov.yamblzweather;

public final class Config {

    private Config() {
    }

    public static final int TIMEOUT = 5000;

    public static final int DEFAULT_CITY_ID = 524901;

    public static class API {
        public static final String KEY_FIELD = "APPID";
        public static final String KEY = "b3f1079128fa7ee84d98e92fecc133fa";
    }

    public static class PREFS_KEYS {
        public static final String FIRST_LAUNCH = "first_launch";
        public static final String LOCATION = "location_key";
        public static final String LOCATION_DEFAULT = String.valueOf(DEFAULT_CITY_ID);
    }
}
