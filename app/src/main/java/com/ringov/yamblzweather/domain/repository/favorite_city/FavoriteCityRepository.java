package com.ringov.yamblzweather.domain.repository.favorite_city;

import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface FavoriteCityRepository {

    Single<List<UICityFavorite>> getAll();

    Single<String> getSelectedCityName();

    Completable select(UICityFavorite city);

    Completable add(String cityName);

    Completable remove(UICityFavorite city);
}
