package com.ringov.yamblzweather.model.repositories.settings;

import com.ringov.yamblzweather.model.repositories.base.BaseRepository;

/**
 * Created by ringov on 16.07.17.
 */

public interface SettingsRepository extends BaseRepository {
    boolean isNotificationsEnabled();

    long getUpdateInterval();
}
