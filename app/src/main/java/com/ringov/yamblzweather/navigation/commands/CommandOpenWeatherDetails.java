package com.ringov.yamblzweather.navigation.commands;

import com.ringov.yamblzweather.navigation.base.Command;

public class CommandOpenWeatherDetails extends Command {

    private final long time;

    public CommandOpenWeatherDetails(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
