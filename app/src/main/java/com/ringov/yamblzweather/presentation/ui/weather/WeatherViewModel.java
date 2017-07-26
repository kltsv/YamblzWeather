package com.ringov.yamblzweather.presentation.ui.weather;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.ringov.yamblzweather.App;
import com.ringov.yamblzweather.model.background_service.WeatherUpdateJob;
import com.ringov.yamblzweather.model.repositories.weather.WeatherRepository;
import com.ringov.yamblzweather.presentation.base.BaseLiveData;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;
import com.ringov.yamblzweather.presentation.data.UIWeather;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by ringov on 12.07.17.
 */

public class WeatherViewModel extends BaseViewModel {

    private BaseLiveData<Boolean> loadingData = new BaseLiveData<>();
    private BaseLiveData<UIWeather> weatherData = new BaseLiveData<>();
    private BaseLiveData<Throwable> errorData = new BaseLiveData<>();

    @Inject
    WeatherRepository repository;

    public WeatherViewModel() {
        App.getComponent().inject(this);
        disposables.add(
                repository
                        .getLastWeatherInfo()
                        .concatWith(repository.updateWeather())
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                uiWeather -> weatherData.updateValue(uiWeather),
                                throwable -> errorData.updateValue(throwable)
                        )
        );

        Set<JobRequest> requests = JobManager.instance().getAllJobRequestsForTag(WeatherUpdateJob.TAG);

        if (requests.isEmpty()) {
            WeatherUpdateJob.schedule();
        }
    }

    void observe(
            LifecycleOwner owner,
            Observer<Boolean> loadingObserver,
            Observer<UIWeather> weatherObserver,
            Observer<Throwable> errorObserver
    ) {
        loadingData.observe(owner, loadingObserver);
        weatherData.observe(owner, weatherObserver);
        errorData.observe(owner, errorObserver);
    }

    // View callbacks
    void onRefresh() {
        disposables.add(
                repository.updateWeatherIfDataIsOld()
                        .doOnSubscribe(disposable -> loadingData.updateValue(true))
                        .doFinally(() -> loadingData.updateValue(false))
                        .subscribe(
                                uiWeather -> weatherData.updateValue(uiWeather),
                                throwable -> errorData.updateValue(throwable)
                        )
        );
    }
}
