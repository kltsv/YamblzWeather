package com.ringov.yamblzweather.presentation.ui.main.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ringov.yamblzweather.Prefs;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.data.service.AlarmReceiver;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = "SettingsFragment";

    @BindString(R.string.prefs_interval_key)
    String INTERVAL_KEY;
    @BindString(R.string.prefs_push_key)
    String PUSH_KEY;

    private Unbinder unbinder;

    private SharedPreferences prefs;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.prefs_title);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(INTERVAL_KEY)) {
            AlarmReceiver alarmReceiver = new AlarmReceiver();

            final String interval = Prefs.getString(
                    getContext(), R.string.prefs_interval_key, R.string.prefs_interval_default);

            alarmReceiver.setAlarm(getContext(), Integer.valueOf(interval));
        } else if (key.equals(PUSH_KEY)) {
            AlarmReceiver alarmReceiver = new AlarmReceiver();

            final boolean pushEnabled = Prefs.getBoolean(
                    getContext(), R.string.prefs_push_key, R.bool.prefs_push_default);

            if (pushEnabled) {
                final String interval = Prefs.getString(
                        getContext(), R.string.prefs_interval_key, R.string.prefs_interval_default);

                alarmReceiver.setAlarm(getContext(), Integer.valueOf(interval));
            } else {
                alarmReceiver.cancelAlarm(getContext());
            }
        }
    }
}
