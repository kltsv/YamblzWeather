package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.domain.repository.favorite_city.FavoriteCityRepository;
import com.ringov.yamblzweather.navigation.base.Command;
import com.ringov.yamblzweather.navigation.base.Router;
import com.ringov.yamblzweather.navigation.commands.CommandOpenAboutScreen;
import com.ringov.yamblzweather.presentation.ui.main.MainViewModel;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MainViewModelTest {

    private MainViewModel viewModel;

    private RouterMock router;

    private FavoriteCityRepository favoriteCityRepository;

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
        this.favoriteCityRepository = mock(FavoriteCityRepository.class);
        Router router = this.router;
        viewModel = new MainViewModel(router, favoriteCityRepository);
    }

    @Test
    public void navigationQueueTest() {
        viewModel.onAboutNavigation();
        assertTrue(router.commandsQueue.size() == 1);
        viewModel.onAboutNavigation();
        assertTrue(router.commandsQueue.size() == 2);
        viewModel.onAboutNavigation();
        assertTrue(router.commandsQueue.size() == 3);
    }

    @Test
    public void isNavigationCorrectTest() {
        viewModel.onAboutNavigation();
        assertTrue(router.commandsQueue.get(0) instanceof CommandOpenAboutScreen);
    }
}
