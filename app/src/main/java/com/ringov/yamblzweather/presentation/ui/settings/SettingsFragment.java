package com.ringov.yamblzweather.presentation.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.data.background_service.WeatherUpdateJob;
import com.ringov.yamblzweather.domain.settings.SettingsRepository;

import java.util.Set;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = "SettingsFragment";
    @Inject
    SettingsRepository settings;
    SharedPreferences prefs;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        App.getComponent().inject(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_settings);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // todo move this logic to another layer
        // update interval if update
        Set<JobRequest> requests = JobManager.instance().getAllJobRequestsForTag(WeatherUpdateJob.TAG);
        if (!requests.isEmpty()) {
            for (JobRequest jr : requests) {
                long lastInterval = jr.getIntervalMs();
                long settingsInterval = settings.getUpdateInterval();
                if (lastInterval != settingsInterval) {
                    jr.cancelAndEdit().setPeriodic(settingsInterval).build();
                }
            }
        }
    }
}
