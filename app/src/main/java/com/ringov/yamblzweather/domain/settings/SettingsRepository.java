package com.ringov.yamblzweather.domain.settings;

public interface SettingsRepository {

    boolean isNotificationsEnabled();

    long getUpdateInterval();
}
