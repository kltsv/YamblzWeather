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
import com.ringov.yamblzweather.presentation.ui.main.add_city.AddCityFragment;
import com.ringov.yamblzweather.presentation.ui.main.settings.SettingsFragment;
import com.ringov.yamblzweather.presentation.ui.splash.SplashActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    private static final String DEFAULT_CITY = "Moscow";

    private Context context;

    @Rule
    public ActivityTestRule<SplashActivity> splashActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void prepare() {
        context = mainActivityTestRule.getActivity().getApplicationContext();
    }

    @Test
    public void checkHasDefaultCityValue() {
        assertEquals(DEFAULT_CITY, mainActivityTestRule.getActivity().getTitle());
    }

    @Test
    public void openAddCityScreen() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.rl_add_city)).perform(click());

        String activityTitle = mainActivityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.add_city_title);
        assertEquals(expectedTitle, activityTitle);

        Fragment fragment =
                mainActivityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(AddCityFragment.class.getSimpleName());
        assertTrue(fragment instanceof AddCityFragment);
    }

    @Test
    public void openSettingsScreen() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_settings));

        Thread.sleep(600);

        String activityTitle = mainActivityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.prefs_title);
        assertEquals(expectedTitle, activityTitle);

        Fragment fragment =
                mainActivityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(SettingsFragment.class.getSimpleName());
        assertTrue(fragment instanceof SettingsFragment);
    }

    @Test
    public void openAboutScreen() throws InterruptedException {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_about));

        Thread.sleep(1000);

        String activityTitle = mainActivityTestRule.getActivity().getTitle().toString();
        String expectedTitle = context.getString(R.string.about_title);
        assertEquals(expectedTitle, activityTitle);

        Fragment fragment =
                mainActivityTestRule.getActivity().getSupportFragmentManager()
                        .findFragmentByTag(AboutFragment.class.getSimpleName());
        assertTrue(fragment instanceof AboutFragment);
    }
}
