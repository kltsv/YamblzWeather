package com.ringov.yamblzweather.navigation.commands;

import com.ringov.yamblzweather.navigation.base.Command;

public class CommandOpenWeatherDetails extends Command {

    private final long time;

    private final int cityId;

    public CommandOpenWeatherDetails(long time, int cityId) {
        this.time = time;
        this.cityId = cityId;
    }

    public long getTime() {
        return time;
    }

    public int getCityId() {
        return cityId;
    }
}
