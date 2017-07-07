package com.ringov.yamblzweather.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.routing.ScreenView;

/**
 * Created by ringov on 07.07.17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements ScreenView<SettingsFragment> {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public SettingsFragment getFragment() {
        return this;
    }
}
