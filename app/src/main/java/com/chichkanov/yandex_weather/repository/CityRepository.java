package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;

import io.reactivex.Maybe;
import io.reactivex.Observable;


interface CityRepository {
    Observable<CitySuggestion> getCitySuggestion(String cityName);

    void setCurrentCity(Prediction city);

    Maybe<City> getCurrentCity();
}
