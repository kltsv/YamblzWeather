package com.ivanantsiferov.yamblzweather.ui;

import android.content.Context;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.ui.main.MainActivity;
import com.ringov.yamblzweather.presentation.ui.main.about.AboutFragment;
import com.ringov.yamblzweather.presentation.ui.main.location.LocationFragment;
import com.ringov.yamblzweather.presentation.ui.main.settings.SettingsFragment;
import com.ringov.yamblzweather.presentation.ui.main.weather.WeatherFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NavDrawerNavigationTest {

    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void prepare() {
        context = activityTestRule.getActivity().getApplicationContext();
    }

    @Test
    public void openWeatherScreen() throws InterruptedException {
        // Open drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        // Click on weather menu item
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_weather));
        // Wait for animations and fragment transaction to complete
        Thread.sleep(1000);
        // Check if title changed correctly
        String activityTitle = activityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.title_weather);
        assertEquals(expectedTitle, activityTitle);
        // Check if weather fragment is showing
        Fragment fragment =
                activityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(WeatherFragment.class.getSimpleName());
        assertTrue(fragment instanceof WeatherFragment);
    }

    @Test
    public void openLocationScreen() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_location));

        Thread.sleep(1000);

        String activityTitle = activityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.title_location);
        assertEquals(expectedTitle, activityTitle);
        Fragment fragment =
                activityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(LocationFragment.class.getSimpleName());
        assertTrue(fragment instanceof LocationFragment);
    }

    @Test
    public void openSettingsScreen() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_settings));

        Thread.sleep(1000);

        String activityTitle = activityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.title_settings);
        assertEquals(expectedTitle, activityTitle);
        Fragment fragment =
                activityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(SettingsFragment.class.getSimpleName());
        assertTrue(fragment instanceof SettingsFragment);
    }

    @Test
    public void openAboutScreen() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_about));

        Thread.sleep(1000);

        String activityTitle = activityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.title_about);
        assertEquals(expectedTitle, activityTitle);
        Fragment fragment =
                activityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(AboutFragment.class.getSimpleName());
        assertTrue(fragment instanceof AboutFragment);
    }
}
