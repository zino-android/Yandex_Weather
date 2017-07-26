package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.places.CitySuggestion;

import io.reactivex.Observable;


interface CityRepository {
    Observable<CitySuggestion> getCitySuggestion(String cityName);

    void setCurrentCity(String city);

    String getCurrentCity();
}
