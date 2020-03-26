package com.ivanantsiferov.yamblzweather.repository;

import com.ivanantsiferov.yamblzweather.CurrentThreadExecutor;
import com.ringov.yamblzweather.data.database.dao.CityDAO;
import com.ringov.yamblzweather.data.database.dao.FavoriteCityDAO;
import com.ringov.yamblzweather.domain.Mapper;
import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepositoryImpl;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteCityRepositoryTest {

    private FavoriteCityRepository favoriteCityRepository;

    private FavoriteCityDAO favoriteCityDAO;
    private CityDAO cityDAO;

    @Before
    public void prepare() {
        Scheduler scheduler = Schedulers.from(new CurrentThreadExecutor());

        favoriteCityDAO = mock(FavoriteCityDAO.class);
        cityDAO = mock(CityDAO.class);

        favoriteCityRepository = new FavoriteCityRepositoryImpl(
                scheduler, scheduler, scheduler, favoriteCityDAO, cityDAO);

        when(favoriteCityDAO.getEnabled()).thenReturn(RepositoryTestUtils.enabledCity());
        when(favoriteCityDAO.getAll())
                .thenReturn(Flowable.just(RepositoryTestUtils.dbFavoriteCityList()));
        when(favoriteCityDAO.getFirst()).thenReturn(RepositoryTestUtils.favoriteCity());
        when(favoriteCityDAO.getById(524901)).thenReturn(RepositoryTestUtils.enabledCity());
        when(favoriteCityDAO.getById(123456)).thenReturn(RepositoryTestUtils.favoriteCity());

        when(cityDAO.getByName("Kathmandu")).thenReturn(RepositoryTestUtils.dbCityKathmandu());
    }

    @Test
    public void allSortsOfGet() {
        favoriteCityRepository.getAll().test()
                .assertValue(uiCityFavorites -> uiCityFavorites.size() == 2);
        verify(favoriteCityDAO, times(1)).getAll();

        favoriteCityRepository.getSelectedCityName().test()
                .assertValue(s -> s.equals(RepositoryTestUtils.enabledCity().getCity_name()))
                .assertComplete();
    }

    @Test
    public void removeEnabled() {
        UICityFavorite uiCityFavorite = Mapper.DBtoUIFavoriteCity(RepositoryTestUtils.enabledCity());
        favoriteCityRepository.remove(uiCityFavorite).test().assertComplete();
        verify(favoriteCityDAO, times(1)).getById(uiCityFavorite.getCityId());
        verify(favoriteCityDAO, times(1)).delete(any());
        verify(favoriteCityDAO, times(1)).getFirst();
        verify(favoriteCityDAO, times(1)).insert(any());
    }

    @Test
    public void add() {
        favoriteCityRepository.add("Kathmandu").test();
        verify(cityDAO, times(1)).getByName("Kathmandu");
        verify(favoriteCityDAO, times(1)).insert(any());
    }

    @Test
    public void select() {
        UICityFavorite uiCityFavorite = Mapper.DBtoUIFavoriteCity(RepositoryTestUtils.favoriteCity());
        favoriteCityRepository.select(uiCityFavorite).test();
        verify(favoriteCityDAO, times(1)).getEnabled();
        verify(favoriteCityDAO, times(2)).insert(any());
        verify(favoriteCityDAO, times(1)).getById(uiCityFavorite.getCityId());
    }
}
