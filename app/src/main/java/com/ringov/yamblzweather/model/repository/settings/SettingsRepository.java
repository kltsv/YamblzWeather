package com.ringov.yamblzweather.model.repository.settings;

import com.ringov.yamblzweather.model.repository.base.BaseRepository;

/**
 * Created by ringov on 16.07.17.
 */

public interface SettingsRepository extends BaseRepository {

    boolean isNotificationsEnabled();

    long getUpdateInterval();
}
