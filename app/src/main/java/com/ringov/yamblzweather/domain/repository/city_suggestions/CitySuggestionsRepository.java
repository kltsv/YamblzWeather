package com.ringov.yamblzweather.domain.repository.city_suggestions;

import java.util.List;

import io.reactivex.Single;

public interface CitySuggestionsRepository {

    Single<List<String>> getSuggestions(String input);
}
