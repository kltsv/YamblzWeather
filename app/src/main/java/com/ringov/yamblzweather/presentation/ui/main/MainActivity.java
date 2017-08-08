package com.ringov.yamblzweather.presentation.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.navigation.base.Command;
import com.ringov.yamblzweather.navigation.base.Navigator;
import com.ringov.yamblzweather.navigation.base.NavigatorBinder;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenLocationScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherDetails;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherScreen;
import com.ringov.yamblzweather.presentation.base.BaseMvvmActivity;
import com.ringov.yamblzweather.presentation.ui.details.DetailsActivity;
import com.ringov.yamblzweather.presentation.ui.details.DetailsFragment;
import com.ringov.yamblzweather.presentation.ui.main.about.AboutFragment;
import com.ringov.yamblzweather.presentation.ui.main.forecast.ForecastFragment;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class MainActivity extends BaseMvvmActivity<MainViewModel> implements
        NavigationView.OnNavigationItemSelectedListener,
        HasSupportFragmentInjector,
        Navigator {

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    NavigatorBinder navigatorBinder;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @IdRes
    private static final int FRAGMENT_CONTAINER = R.id.container;
    @IdRes
    private static final int FRAGMENT_DETAILS_CONTAINER = R.id.details_container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    private boolean toolBarNavigationListenerIsRegistered = false;
    private boolean twoPaneMode = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeToolbar();
        initializeDrawer();

        FrameLayout detailsContainer = ButterKnife.findById(this, R.id.details_container);
        if (detailsContainer != null)
            twoPaneMode = true;

        if (savedInstanceState == null) {
            navigateToForecastScreen();
        } else if (isNotOnForecastScreen()) {
            showBackButton(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (toolBarNavigationListenerIsRegistered) {
            navigateToForecastScreen();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_forecast:
                viewModel.onForecastNavigation();
                break;
            case R.id.nav_location:
                viewModel.onLocationNavigation();
                break;
            case R.id.nav_about:
                viewModel.onAboutNavigation();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorBinder.setNavigator(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorBinder.removeNavigator();
    }

    @Override
    public boolean executeCommand(Command command) {
        if (command instanceof CommandOpenWeatherDetails) return openDetailsScreen(command);
        else if (command instanceof CommandOpenWeatherScreen) return navigateToForecastScreen();
        else if (command instanceof CommandOpenLocationScreen) return navigateToLocationScreen();
        else if (command instanceof CommandOpenAboutScreen) return navigateToAboutScreen();
        else return false;
    }

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
    }

    @Override
    protected void attachInputListeners() {}

    private void initializeToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initializeDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * See https://stackoverflow.com/questions/36579799/
     */
    private void showBackButton(boolean enable) {
        if (enable) {
            drawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!toolBarNavigationListenerIsRegistered) {
                drawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());

                toolBarNavigationListenerIsRegistered = true;
            }
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(null);
            toolBarNavigationListenerIsRegistered = false;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    // Navigation helper methods
    private boolean isNotOnForecastScreen() {
        return getSupportFragmentManager().findFragmentByTag(ForecastFragment.TAG) == null;
    }

    private boolean navigateToForecastScreen() {
        if (isNotOnForecastScreen()) {
            showBackButton(false);
            replaceFragment(ForecastFragment.newInstance(), FRAGMENT_CONTAINER);
        }
        return true;
    }

    private boolean navigateToLocationScreen() {
        showBackButton(true);
        replaceFragment(LocationFragment.newInstance(), FRAGMENT_CONTAINER);
        return true;
    }

    private boolean navigateToAboutScreen() {
        showBackButton(true);
        replaceFragment(AboutFragment.newInstance(), FRAGMENT_CONTAINER);
        return true;
    }

    private boolean openDetailsScreen(Command command) {
        CommandOpenWeatherDetails c = (CommandOpenWeatherDetails) command;
        long time = c.getTime();
        Timber.d("Open weather details, two pane = " + twoPaneMode);
        if (twoPaneMode) {
            replaceFragment(DetailsFragment.newInstance(time), FRAGMENT_DETAILS_CONTAINER);
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.ARG_TIME, time);
            startActivity(intent);
        }

        return true;
    }
}
