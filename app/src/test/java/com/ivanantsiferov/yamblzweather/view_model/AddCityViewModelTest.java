package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.presentation.ui.main.add_city.AddCityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddCityViewModelTest extends ViewModelTest {

    private AddCityViewModel addCityViewModel;

    @Before
    public void prepare() {
        addCityViewModel =
                new AddCityViewModel(router, citySuggestionsRepository, favoriteCityRepository);

        when(favoriteCityRepository.add(any())).thenReturn(Completable.complete());
        when(citySuggestionsRepository.getSuggestions(anyString()))
                .thenReturn(Single.just(new ArrayList<>()));
    }

    @Test
    public void userTypingCityName() {
        addCityViewModel.onInputChanges("mosc");
        verify(citySuggestionsRepository, times(1)).getSuggestions("mosc");
    }

    @Test
    public void userSelectCity() {
        addCityViewModel.onCitySelected("Moscow");
        verify(favoriteCityRepository, times(1)).add("Moscow");
        verify(router, times(1)).execute(any());
    }
}
