package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.presentation.entity.UICityFavorite;

class ViewModelTestUtils {

    private ViewModelTestUtils() {
    }

    static UICityFavorite enabledFavoriteCity() {
        return new UICityFavorite.Builder().enabled(true).build();
    }

    static UICityFavorite notEnabledFavoriteCity() {
        return new UICityFavorite.Builder().enabled(false).build();
    }
}
