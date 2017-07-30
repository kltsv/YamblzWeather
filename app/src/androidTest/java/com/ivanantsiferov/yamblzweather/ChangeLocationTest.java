package com.ivanantsiferov.yamblzweather;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.R;
import com.ringov.yamblzweather.presentation.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ChangeLocationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkHasDefaultLocationValue() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_weather));
        onView(withId(R.id.tv_location)).check(matches(not(withText(""))));

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_location));
        onView(withId(R.id.atv_location)).check(matches(not(withText(""))));
    }

    /**
     * This test works only after first app launch - because data for city suggestions
     * need to be loaded before running this test.
     */
    @Test
    public void citySettingsSuggestionsWork() throws InterruptedException {
        // Open location screen and type something
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_location));
        onView(withId(R.id.atv_location)).perform(clearText(), typeText("mosco"));

        // Wait for debounce and DB query
        Thread.sleep(600);

        String expectedSuggestion = "Moscow";

        // Check, that app show right suggestion and click on it
        onData(instanceOf(String.class))
                .inRoot(RootMatchers.isPlatformPopup())
                .atPosition(0)
                .check(matches(withText(expectedSuggestion)))
                .perform(click());

        onView(withId(R.id.atv_location)).check(matches(withText(expectedSuggestion)));

        // Go to weather screen
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_weather));

        // Check, that location has changed here too
        onView(withId(R.id.tv_location)).check(matches(withText(expectedSuggestion)));

        // Go back to location screen
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navigation_view)).perform(NavigationViewActions.navigateTo(R.id.nav_location));

        // Check, that location has been saved
        onView(withId(R.id.atv_location)).check(matches(withText(expectedSuggestion)));
    }
}
