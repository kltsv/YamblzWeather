package com.ivanantsiferov.yamblzweather.repository;

import com.ivanantsiferov.yamblzweather.CurrentThreadExecutor;
import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.domain.repository.city_suggestions.CitySuggestionsRepository;
import com.ringov.yamblzweather.domain.repository.city_suggestions.CitySuggestionsRepositoryImpl;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CitySuggestionsRepositoryTest {

    private CitySuggestionsRepository citySuggestionsRepository;

    private CityDAO cityDAO;

    @Before
    public void prepare() {
        Scheduler scheduler = Schedulers.from(new CurrentThreadExecutor());

        cityDAO = mock(CityDAO.class);

        citySuggestionsRepository = new CitySuggestionsRepositoryImpl(
                scheduler, scheduler, scheduler, cityDAO
        );

        when(cityDAO.getSuggestions(anyString(), anyInt()))
                .thenReturn(RepositoryTestUtil.suggestions());
    }

    @Test
    public void testSuggestions() {
        citySuggestionsRepository.getSuggestions("string")
                .test().assertValue(strings -> strings.get(0).equals("Moscow"));
        verify(cityDAO, atLeastOnce()).getSuggestions(anyString(), anyInt());
    }
}
