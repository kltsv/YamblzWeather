package com.ivanantsiferov.yamblzweather.navigation;

import com.ringov.yamblzweather.navigation.RouterHolder;
import com.ringov.yamblzweather.navigation.base.Navigator;
import com.ringov.yamblzweather.navigation.base.NavigatorBinder;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandCloseDrawer;
import com.ringov.yamblzweather.navigation.commands.CommandNavigatorAttached;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAddCityScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenForecastScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenSettingsScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherDetails;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class RouterHolderTest {

    private RouterHolder routerHolder;

    private Router router;
    private NavigatorBinder navigatorBinder;

    // Executes all command exclude CommandOpenAddCityScreen
    private Navigator navigator = (Navigator) command -> {
        if (command instanceof CommandNavigatorAttached) return false;
        else if (command instanceof CommandOpenWeatherDetails) return true;
        else if (command instanceof CommandOpenForecastScreen) return true;
        else if (command instanceof CommandOpenAddCityScreen) return false;
        else if (command instanceof CommandOpenAboutScreen) return true;
        else if (command instanceof CommandOpenSettingsScreen) return true;
        else if (command instanceof CommandCloseDrawer) return true;
        else throw new IllegalArgumentException();
    };
    // Executes only CommandOpenAddCityScreen
    private Navigator addCityNavigator =
            (Navigator) command -> command instanceof CommandOpenAddCityScreen;

    @Before
    public void prepare() {
        routerHolder = new RouterHolder();
        this.router = routerHolder;
        this.navigatorBinder = routerHolder;
    }

    @Test
    public void attachDetachNavigator() {
        // Create command that need to be queued
        CommandCloseDrawer commandCloseDrawer = new CommandCloseDrawer();
        commandCloseDrawer.addToQueue = true;
        // Execute it without navigator attached
        router.execute(commandCloseDrawer);
        assertEquals(1, routerHolder.commandsQueue.size());
        // Attach navigator, command executes
        navigatorBinder.setNavigator(navigator);
        assertEquals(0, routerHolder.commandsQueue.size());
        // Detach and execute
        navigatorBinder.removeNavigator();
        router.execute(commandCloseDrawer);
        router.execute(commandCloseDrawer);
        assertEquals(2, routerHolder.commandsQueue.size());
    }

    @Test
    public void queueTest() {
        CommandOpenForecastScreen commandOpenForecastScreen = new CommandOpenForecastScreen();
        // There is no navigator and we dont want add command to queue
        commandOpenForecastScreen.addToQueue = false;
        router.execute(commandOpenForecastScreen);
        assertEquals(0, routerHolder.commandsQueue.size());
        // Now we wand add it to queue
        commandOpenForecastScreen.addToQueue = true;
        router.execute(commandOpenForecastScreen);
        assertEquals(1, routerHolder.commandsQueue.size());
        // Attach navigator, run queue
        navigatorBinder.setNavigator(navigator);
        assertEquals(0, routerHolder.commandsQueue.size());
    }

    @Test
    public void differentNavigators() {
        navigatorBinder.setNavigator(navigator);
        // Execute command, that cannot be performed by this navigator
        CommandOpenAddCityScreen command = new CommandOpenAddCityScreen();
        command.addToQueue = true;
        router.execute(command);
        assertEquals(1, routerHolder.commandsQueue.size());
        // Attach new navigator and execute queue
        navigatorBinder.removeNavigator();
        navigatorBinder.setNavigator(addCityNavigator);
        assertEquals(0, routerHolder.commandsQueue.size());
    }
}
