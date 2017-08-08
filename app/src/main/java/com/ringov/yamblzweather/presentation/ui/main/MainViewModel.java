package com.ringov.yamblzweather.presentation.ui.main;

import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenLocationScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherScreen;
import com.ringov.yamblzweather.presentation.base.BaseViewModel;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {

    private Router router;

    @Inject
    public MainViewModel(Router router) {
        this.router = router;
    }

    // View callbacks
    public void onForecastNavigation() {
        router.execute(new CommandOpenWeatherScreen());
    }

    public void onLocationNavigation() {
        router.execute(new CommandOpenLocationScreen());
    }

    public void onAboutNavigation() {
        router.execute(new CommandOpenAboutScreen());
    }
}
