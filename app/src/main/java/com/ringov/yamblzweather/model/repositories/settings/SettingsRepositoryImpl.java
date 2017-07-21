package com.ringov.yamblzweather.model.repositories.settings;

import com.ringov.yamblzweather.model.db.Database;
import com.ringov.yamblzweather.model.repositories.base.BaseRepositoryImpl;

import javax.inject.Singleton;

/**
 * Created by ringov on 16.07.17.
 */

@Singleton
public class SettingsRepositoryImpl extends BaseRepositoryImpl implements SettingsRepository {
    @Override
    public boolean isNotificationsEnabled() {
        return Database.getInstance().isNotificationEnabled();
    }

    @Override
    public long getUpdateInterval() {
        return Database.getInstance().getUpdateInterval();
    }
}
