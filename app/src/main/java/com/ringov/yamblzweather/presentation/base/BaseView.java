package com.ringov.yamblzweather.presentation.base;

/**
 * Created by ringov on 21.07.17.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showError(String message);
}
