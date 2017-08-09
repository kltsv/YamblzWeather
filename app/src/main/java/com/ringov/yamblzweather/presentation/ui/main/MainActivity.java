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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;
import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.navigation.base.Command;
import com.ringov.yamblzweather.navigation.base.Navigator;
import com.ringov.yamblzweather.navigation.base.NavigatorBinder;
import com.ringov.yamblzweather.navigation.commands.CommandCloseDrawer;
import com.ringov.yamblzweather.navigation.commands.CommandNavigatorAttached;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAddCityScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherDetails;
import com.ringov.yamblzweather.navigation.commands.CommandOpenForecastScreen;
import com.ringov.yamblzweather.presentation.base.BaseMvvmActivity;
import com.ringov.yamblzweather.presentation.entity.UICityFavorite;
import com.ringov.yamblzweather.presentation.ui.details.DetailsActivity;
import com.ringov.yamblzweather.presentation.ui.details.DetailsFragment;
import com.ringov.yamblzweather.presentation.ui.main.about.AboutFragment;
import com.ringov.yamblzweather.presentation.ui.main.add_city.AddCityFragment;
import com.ringov.yamblzweather.presentation.ui.main.forecast.ForecastFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

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

    @Nullable
    @BindView(R.id.details_container)
    FrameLayout detailsContainer;

    private RecyclerView citiesRecyclerView;
    private RelativeLayout addCityView;

    private ActionBarDrawerToggle drawerToggle;

    private CityAdapter cityAdapter;

    private boolean toolBarNavigationListenerIsRegistered = false;
    private boolean twoPaneMode = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initDrawer();
        initCitiesRecycler();

        FrameLayout detailsContainer = ButterKnife.findById(this, R.id.details_container);
        if (detailsContainer != null)
            twoPaneMode = true;

        if (savedInstanceState == null) {
            navigateToForecastScreen(false);
        } else if (isNotOnForecastScreen()) {
            showBackButton(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
        } else if (toolBarNavigationListenerIsRegistered) {
            navigateToForecastScreen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about:
                viewModel.onAboutNavigation();
                break;
        }

        closeDrawer();
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
    protected void onDestroy() {
        super.onDestroy();
        cityAdapter.destroy();
    }

    @Override
    public boolean executeCommand(Command command) {
        // Its critical to check for CommandNavigatorAttached and return false,
        // otherwise navigation command queue will break.
        if (command instanceof CommandNavigatorAttached) return false;
        else if (command instanceof CommandOpenWeatherDetails) return openDetailsScreen(command);
        else if (command instanceof CommandOpenForecastScreen) return navigateToForecastScreen(true);
        else if (command instanceof CommandOpenAddCityScreen) return navigateToAddCityScreen();
        else if (command instanceof CommandOpenAboutScreen) return navigateToAboutScreen();
        else if (command instanceof CommandCloseDrawer) return closeDrawer();
        else throw new IllegalArgumentException(
                    "Trying to execute unknown command: " + command.getClass().getSimpleName());
    }

    @Override
    protected void onViewModelAttach() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass());
    }

    @Override
    protected void attachInputListeners() {
        viewModel.observe(this, this::showCities);

        disposables.add(
                RxView.clicks(addCityView).subscribe(o -> viewModel.onAddCityNavigation()));

        disposables.add(
                cityAdapter.getOnItemClickObservable()
                        .subscribe(cityFavorite -> viewModel.onFavoriteCityClick(cityFavorite))
        );

        disposables.add(
                cityAdapter.getOnItemRemoveClickObservable()
                        .subscribe(cityFavorite -> viewModel.onRemoveCityClick(cityFavorite))
        );
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        citiesRecyclerView = ButterKnife.findById(headerView, R.id.rv_cities);
        addCityView = ButterKnife.findById(headerView, R.id.rl_add_city);
    }

    private void initCitiesRecycler() {
        cityAdapter = new CityAdapter();
        citiesRecyclerView.setHasFixedSize(true);
        citiesRecyclerView.setNestedScrollingEnabled(false);
        citiesRecyclerView.setAdapter(cityAdapter);
    }

    private void showCities(List<UICityFavorite> cityFavorites) {
        cityAdapter.replace(cityFavorites);
    }

    /**
     * See https://stackoverflow.com/questions/36579799/
     */
    private void showBackButton(boolean enable) {
        showHideDetailsContainer(!enable);

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
    private boolean closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showHideDetailsContainer(boolean show) {
        if (twoPaneMode && detailsContainer != null) {
            if (show)
                detailsContainer.setVisibility(View.VISIBLE);
            else
                detailsContainer.setVisibility(View.GONE);
        }
    }

    private boolean isNotOnForecastScreen() {
        return getSupportFragmentManager().findFragmentByTag(ForecastFragment.TAG) == null;
    }

    private boolean navigateToForecastScreen(boolean ignore) {
        if (ignore || isNotOnForecastScreen()) {
            showBackButton(false);
            replaceFragment(ForecastFragment.newInstance(), FRAGMENT_CONTAINER);
            closeDrawer();
        }
        return true;
    }

    private boolean navigateToAddCityScreen() {
        showBackButton(true);
        replaceFragment(AddCityFragment.newInstance(), FRAGMENT_CONTAINER);
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
