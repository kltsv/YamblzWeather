package com.ringov.yamblzweather.viewmodel.base;

/**
 * Created by ringov on 21.07.17.
 */

public class StateData {

    private boolean loading;
    private String error;

    public void loading() {
        this.loading = true;
    }

    public void loaded() {
        this.loading = false;
    }

    public void error(String message) {
        this.error = message;
    }

    public boolean isError() {
        return this.error != null && !this.error.isEmpty();
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isLoaded() {
        return !loading;
    }

    public String getErrorMessage() {
        return this.error;
    }
}
