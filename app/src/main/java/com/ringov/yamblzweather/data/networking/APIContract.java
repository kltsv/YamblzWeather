package com.ringov.yamblzweather.data.networking;

final class APIContract {

    private APIContract() {
    }

    // Api endpoints
    static final String ENDPOINT_DAILY = "forecast/daily";

    // Query params
    static final String PARAM_API_KEY = "appid";
    static final String PARAM_CITY_ID = "id";
    static final String PARAM_COUNT = "cnt";
}
