package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.navigation.base.Command;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenLocationScreen;
import com.ringov.yamblzweather.navigation.commands.CommandOpenWeatherScreen;
import com.ringov.yamblzweather.presentation.ui.main.MainViewModel;

import static junit.framework.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MainViewModelTest {

    private MainViewModel viewModel;

    private RouterMock router;

    private static class RouterMock implements Router {

        ArrayList<Command> commandsQueue = new ArrayList<>();

        @Override
        public void execute(Command command) {
            commandsQueue.add(command);
        }
    }

    @Before
    public void prepare() {
        this.router = new RouterMock();
        Router router = this.router;
        viewModel = new MainViewModel(router);
    }

    @Test
    public void navigationQueueTest() {
        viewModel.onWeatherNavigation();
        assertTrue(router.commandsQueue.size() == 1);
        viewModel.onWeatherNavigation();
        assertTrue(router.commandsQueue.size() == 2);
        viewModel.onWeatherNavigation();
        assertTrue(router.commandsQueue.size() == 3);
    }

    @Test
    public void isNavigationCorrectTest() {
        viewModel.onWeatherNavigation();
        assertTrue(router.commandsQueue.get(0) instanceof CommandOpenWeatherScreen);
        viewModel.onLocationNavigation();
        assertTrue(router.commandsQueue.get(1) instanceof CommandOpenLocationScreen);
        viewModel.onAboutNavigation();
        assertTrue(router.commandsQueue.get(2) instanceof CommandOpenAboutScreen);
    }
}
