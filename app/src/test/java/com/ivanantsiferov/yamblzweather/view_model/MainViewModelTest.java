package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.presentation.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainViewModelTest extends ViewModelTest {

    private MainViewModel mainViewModel;

    @Before
    public void prepare() {
        when(favoriteCityRepository.getAll()).thenReturn(Flowable.just(new ArrayList<>()));
        when(favoriteCityRepository.remove(any())).thenReturn(Completable.complete());
        when(favoriteCityRepository.select(any())).thenReturn(Completable.complete());

        mainViewModel = new MainViewModel(router, favoriteCityRepository);
    }

    @Test
    public void navigationCallbacks() {
        mainViewModel.onAboutNavigation();
        mainViewModel.onAddCityNavigation();
        mainViewModel.onSettingsNavigation();
        verify(router, times(3)).execute(any());
    }

    @Test
    public void removeSelectedFavoriteCity() {
        mainViewModel.onRemoveCityClick(ViewModelTestUtils.enabledFavoriteCity());
        verify(favoriteCityRepository, times(1)).remove(any());
        verify(router, times(1)).execute(any());
    }

    @Test
    public void removeNotSelectedFavoriteCity() {
        mainViewModel.onRemoveCityClick(ViewModelTestUtils.notEnabledFavoriteCity());
        verify(favoriteCityRepository, times(1)).remove(any());
        verify(router, never()).execute(any());
    }

    @Test
    public void clickOnSelectedFavoriteCity() {
        mainViewModel.onFavoriteCityClick(ViewModelTestUtils.enabledFavoriteCity());
        verify(favoriteCityRepository, never()).select(any());
        verify(router, times(1)).execute(any());
    }

    @Test
    public void clickOnNotSelectedFavoriteCity() {
        mainViewModel.onFavoriteCityClick(ViewModelTestUtils.notEnabledFavoriteCity());
        verify(favoriteCityRepository, times(1)).select(any());
        verify(router, times(1)).execute(any());
    }
}
