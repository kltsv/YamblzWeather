package com.ivanantsiferov.yamblzweather.view_model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.presentation.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private MainViewModel mainViewModel;

    private Router router;
    private FavoriteCityRepository favoriteCityRepository;

    @Before
    public void prepare() {
        router = mock(Router.class);
        favoriteCityRepository = mock(FavoriteCityRepository.class);

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
