package com.ivanantsiferov.yamblzweather.repository;

import android.support.test.runner.AndroidJUnit4;

import com.ringov.yamblzweather.model.repository.location.LocationRepository;
import com.ringov.yamblzweather.model.repository.location.LocationRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LocationRepositoryImplTest {

    private static final String expected = "Moscow";

    private LocationRepository locationRepository;

    @Before
    public void prepare() {
        locationRepository = new LocationRepositoryImpl();
    }

    @Test
    public void setGetLocation() {
        locationRepository.changeLocation(expected);

        locationRepository.getLocation()
                .subscribe(s -> assertEquals(expected, s));
    }

    @Test
    public void getSuggestions() {
        String input = "mosco";

        locationRepository.getSuggestions(input)
                .subscribe(strings -> assertEquals(expected, strings.get(0)));
    }
}
