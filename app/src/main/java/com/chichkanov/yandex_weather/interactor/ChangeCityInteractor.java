package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.places.CitySuggestion;

import io.reactivex.Observable;


public interface ChangeCityInteractor {
    Observable<CitySuggestion> getCitySuggestion(String cityName);

    void setCurrentCity(String city);
}
