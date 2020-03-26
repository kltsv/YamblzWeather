package com.ringov.yamblzweather.navigation.base;

/**
 * Class, that implements navigator, calls setNavigator when ready to perform in-app navigation.
 * (onResume for activity class)
 * When it can no longer provide navigation, calls removeNavigator.
 * (onPause for activity class)
 */
public interface NavigatorBinder {

    void setNavigator(Navigator navigator);

    void removeNavigator();
}
