package com.ringov.yamblzweather.model.repositories.location;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Ivan on 26.07.2017.
 */

public interface LocationRepository {

    Single<List<String>> getSuggestions(String input);
}
