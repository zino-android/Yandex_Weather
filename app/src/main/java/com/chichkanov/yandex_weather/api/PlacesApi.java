package com.chichkanov.yandex_weather.api;

import com.chichkanov.yandex_weather.model.places.CitySuggestion;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApi {

    @GET("autocomplete/json?types=(cities)")
    Observable<CitySuggestion> getCitySuggest(@Query("input") String input,
                                              @Query("language") String language,
                                              @Query("key") String apiKey);

}
