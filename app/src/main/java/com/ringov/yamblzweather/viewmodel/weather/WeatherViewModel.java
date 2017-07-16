package com.ringov.yamblzweather.viewmodel.weather;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJob;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.viewmodel.base.BaseLiveData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.data.WeatherInfo;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<BaseLiveData<WeatherInfo>, WeatherInfo> {

    @Inject
    WeatherRepository repository;

    public WeatherViewModel() {
        App.getComponent().inject(this);
        addDisposable(repository
                .getLastWeatherInfo()
                .concatWith(repository.updateWeather())
                .subscribe(this::updateData, this::handleError));

        Set<JobRequest> requests = JobManager.instance().getAllJobRequestsForTag(WeatherUpdateJob.TAG);

        if (requests.isEmpty()) {
            WeatherUpdateJob.schedule();
        }
    }

    private void updateData(WeatherInfo weather) {
        getLiveData().updateValue(weather);
    }

    public void onRefresh() {
        addDisposable(repository.updateWeatherIfDataIsOld()
                .subscribe(this::updateData, this::handleError));
    }
}
