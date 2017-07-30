package com.ringov.yamblzweather.model.repository.location;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ivan on 26.07.2017.
 */

public interface LocationRepository {

    Single<String> getLocation();

    void changeLocation(String newValue);

    Single<List<String>> getSuggestions(String input);
}
