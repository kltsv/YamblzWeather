package com.ringov.yamblzweather.domain.settings;

import com.ringov.yamblzweather.data.db.Database;

import javax.inject.Singleton;

@Singleton
public class SettingsRepositoryImpl implements SettingsRepository {

    @Override
    public boolean isNotificationsEnabled() {
        return Database.getInstance().isNotificationEnabled();
    }

    @Override
    public long getUpdateInterval() {
        return Database.getInstance().getUpdateInterval();
    }
}
