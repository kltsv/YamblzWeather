package com.ivanantsiferov.yamblzweather.view_model;

import com.ringov.yamblzweather.domain.repository.weather.WeatherRepository;
import com.ringov.yamblzweather.presentation.entity.UIWeatherDetail;
import com.ringov.yamblzweather.presentation.ui.details.DetailsViewModel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;

public class WeatherDetailsViewModelTest {

    private static final long TEST_TIME = 1234567890;

    private DetailsViewModel viewModel;

    private WeatherRepository weatherRepository;
    private ViewMock viewMock;

    @Before
    public void prepare() {
        weatherRepository = mock(WeatherRepository.class);
        viewMock = new ViewMock();
        viewModel = new DetailsViewModel(weatherRepository);

        Single<UIWeatherDetail> singleMock = Single.just(new UIWeatherDetail());
        when(weatherRepository.getWeather(TEST_TIME)).thenReturn(singleMock);
    }

    /*@Test
    public void startObservingData() {
        viewModel.observe(
                viewMock,
                aBoolean -> viewMock.observeBoolean(aBoolean),
                throwable -> viewMock.observeThrowable(throwable),
                weatherDetail -> viewMock.observeUIWeatherDetail(weatherDetail)
        );
        assertTrue(viewMock.getObserveBooleanCalled() == 1);
        assertTrue(viewMock.getObserveThrowableCalled() == 1);
        assertTrue(viewMock.getObserveUIWeatherDetailCalled() == 1);
    }*/

    @Test
    public void getWeatherByTime() {
        viewModel.showWeatherFor(TEST_TIME);
        verify(weatherRepository, times(1)).getWeather(TEST_TIME);
    }
}
