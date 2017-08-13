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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
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

        when(favoriteCityDAO.getEnabled()).thenReturn(RepositoryTestUtil.enabledCity());
        when(favoriteCityDAO.getAll())
                .thenReturn(Flowable.just(RepositoryTestUtil.dbFavoriteCityList()));
        when(favoriteCityDAO.getFirst()).thenReturn(RepositoryTestUtil.favoriteCity());
        when(favoriteCityDAO.getById(524901)).thenReturn(RepositoryTestUtil.enabledCity());
        when(favoriteCityDAO.getById(123456)).thenReturn(RepositoryTestUtil.favoriteCity());

        when(cityDAO.getByName("Kathmandu")).thenReturn(RepositoryTestUtil.dbCityKathmandu());
    }

    @Test
    public void allSortsOfGet() {
        favoriteCityRepository.getAll().test()
                .assertValue(uiCityFavorites -> uiCityFavorites.size() == 2);
        verify(favoriteCityDAO, atLeastOnce()).getAll();

        favoriteCityRepository.getSelectedCityName().test()
                .assertValue(s -> s.equals(RepositoryTestUtil.enabledCity().getCity_name()))
                .assertComplete();
    }

    @Test
    public void removeEnabled() {
        UICityFavorite uiCityFavorite = Mapper.DBtoUIFavoriteCity(RepositoryTestUtil.enabledCity());
        favoriteCityRepository.remove(uiCityFavorite).test().assertComplete();
        verify(favoriteCityDAO, atLeastOnce()).getById(uiCityFavorite.getCityId());
        verify(favoriteCityDAO, atLeastOnce()).delete(any());
        verify(favoriteCityDAO, atLeastOnce()).getFirst();
        verify(favoriteCityDAO, atLeastOnce()).insert(any());
    }

    @Test
    public void add() {
        favoriteCityRepository.add("Kathmandu").test();
        verify(cityDAO, atLeastOnce()).getByName("Kathmandu");
        verify(favoriteCityDAO, atLeastOnce()).insert(any());
    }

    @Test
    public void select() {
        UICityFavorite uiCityFavorite = Mapper.DBtoUIFavoriteCity(RepositoryTestUtil.favoriteCity());
        favoriteCityRepository.select(uiCityFavorite).test();
        verify(favoriteCityDAO, atLeastOnce()).getEnabled();
        verify(favoriteCityDAO, atLeastOnce()).insert(any());
        verify(favoriteCityDAO, atLeastOnce()).getById(uiCityFavorite.getCityId());
        verify(favoriteCityDAO, atLeastOnce()).insert(any());
    }
}
