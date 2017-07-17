package com.ringov.yamblzweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ringov.yamblzweather.routing.Screen;
import com.ringov.yamblzweather.routing.ScreenRouter;
import com.ringov.yamblzweather.viewmodel.data.UIWeather;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ringov on 07.07.17.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MainViewUpdater {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    ImageView drawerImage;
    TextView drawerTemperature;
    TextView drawerCondition;

    ScreenRouter router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        initializeRouter();
        initializeToolbar();
        initializeDrawer();

        if (savedInstanceState == null) {
            router.open(Screen.Weather);
        }
    }

    private void initializeRouter() {
        router = new ScreenRouter(getSupportFragmentManager(), R.id.container);
    }

    private void initializeToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }

    private void initializeDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        View drawerHeaderLayout = mNavigationView.getHeaderView(0);
        drawerImage = (ImageView) drawerHeaderLayout.findViewById(R.id.drawer_image);
        drawerTemperature = (TextView) drawerHeaderLayout.findViewById(R.id.tv_temperature);
        drawerCondition = (TextView) drawerHeaderLayout.findViewById(R.id.tv_conditions);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawer.closeDrawer(GravityCompat.START);
        router.open(Screen.fromId(item.getItemId()));
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        router.detach();
    }

    @Override
    public void onWeatherUpdate(UIWeather weather) {
        drawerImage.setImageResource(weather.getConditionImage());
        drawerTemperature.setText(getString(R.string.temperature, weather.getTemperature()));
        drawerCondition.setText(weather.getConditionName());
    }
}
