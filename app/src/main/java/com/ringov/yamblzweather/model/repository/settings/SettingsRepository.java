package com.ringov.yamblzweather.model.repository.settings;

import com.ringov.yamblzweather.model.repository.base.BaseRepository;

public interface SettingsRepository extends BaseRepository {

    boolean isNotificationsEnabled();

    long getUpdateInterval();
}
