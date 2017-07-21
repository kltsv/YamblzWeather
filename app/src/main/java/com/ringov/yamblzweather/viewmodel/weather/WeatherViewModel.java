package com.ringov.yamblzweather.viewmodel.weather;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJob;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.ui.weather.WeatherStateData;
import com.ringov.yamblzweather.viewmodel.base.BaseViewModel;
import com.ringov.yamblzweather.viewmodel.data.UIWeather;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel<UIWeather, WeatherStateData> {

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

    @Override
    protected WeatherStateData getInitialState() {
        return new WeatherStateData();
    }

    private void updateData(UIWeather weather) {
        updateValue(weather);
    }

    public void onRefresh() {
        addDisposable(repository.updateWeatherIfDataIsOld()
                .compose(updateStateChanges())
                .subscribe(this::updateData, this::handleError));
    }
}
