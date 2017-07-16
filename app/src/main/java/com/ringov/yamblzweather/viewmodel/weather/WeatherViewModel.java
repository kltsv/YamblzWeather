package com.ringov.yamblzweather.viewmodel.weather;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJob;
import com.ringov.yamblzweather.model.repositories.settings.SettingsRepository;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.data.WeatherInfo;

import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<BaseLiveData<WeatherInfo>, WeatherInfo> {

    @Inject
    WeatherRepository repository;
    @Inject
    SettingsRepository settings;

    public WeatherViewModel() {
        App.getComponent().inject(this);
        addDisposable(repository.getLastWeatherInfo()
                .subscribe(this::updateData, this::handleError));

        Set<JobRequest> requests = JobManager.instance().getAllJobRequestsForTag(WeatherUpdateJob.TAG);

        long now = System.currentTimeMillis();
        if (requests.isEmpty()) {
            WeatherUpdateJob.schedule();
        } else {
            Iterator<JobRequest> iterator = requests.iterator();
            while (iterator.hasNext()) {
                JobRequest jr = iterator.next();
                long lastInterval = jr.getIntervalMs();
                long settingsInterval = settings.getUpdateInterval();
                if (lastInterval != settingsInterval) {
                    jr.cancelAndEdit().setPeriodic(settingsInterval).build();
                }
            }
        }
    }

    private void updateData(WeatherInfo weather) {
        getLiveData().updateValue(weather);
    }

    public void onRefresh() {
        addDisposable(repository.updateWeatherInfo()
                .subscribe(this::updateData, this::handleError));
    }
}
