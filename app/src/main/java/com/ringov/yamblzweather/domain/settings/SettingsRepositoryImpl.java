package com.ringov.yamblzweather.domain.settings;

import com.ringov.yamblzweather.data.db.DatabaseLegacy;

import javax.inject.Singleton;

@Singleton
public class SettingsRepositoryImpl implements SettingsRepository {

    @Override
    public boolean isNotificationsEnabled() {
        return DatabaseLegacy.getInstance().isNotificationEnabled();
    }

    @Override
    public long getUpdateInterval() {
        return DatabaseLegacy.getInstance().getUpdateInterval();
    }
}
