package com.ringov.yamblzweather.domain.repository.city_suggestions;

import android.support.annotation.WorkerThread;

import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.entity.DBCity;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.domain.repository.BaseRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class CitySuggestionsRepositoryImpl extends BaseRepository implements CitySuggestionsRepository {

    private static final int SUGGESTIONS_LIMIT = 5;

    private CityDAO cityDAO;

    @Inject
    public CitySuggestionsRepositoryImpl(
            Scheduler schedulerUI, Scheduler schedulerIO, Scheduler schedulerComputation,
            CityDAO cityDAO
    ) {
        super(schedulerUI, schedulerIO, schedulerComputation);
        this.cityDAO = cityDAO;
    }

    @Override
    public Single<List<String>> getSuggestions(String input) {
        return suggestCities(input).map(Mapper::extractCitiesNamesFromDBCity);
    }

    @WorkerThread
    private Single<List<DBCity>> suggestCities(String input) {
        return Single.fromCallable(() -> cityDAO.getSuggestions(input, SUGGESTIONS_LIMIT));
    }
}
