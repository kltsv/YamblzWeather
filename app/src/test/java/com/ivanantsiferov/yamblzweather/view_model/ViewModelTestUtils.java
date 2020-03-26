package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.presentation.entity.UICityFavorite;
import com.ringov.yamblzweather.presentation.entity.UIWeatherList;

class ViewModelTestUtils {

    private ViewModelTestUtils() {
    }

    static UICityFavorite enabledFavoriteCity() {
        return new UICityFavorite.Builder().enabled(true).build();
    }

    static UICityFavorite notEnabledFavoriteCity() {
        return new UICityFavorite.Builder().enabled(false).build();
    }

    static UIWeatherList uiWeatherList() {
        return new UIWeatherList.Builder().time(0).build();
    }
}
