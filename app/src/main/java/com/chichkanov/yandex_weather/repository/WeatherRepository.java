package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;

import io.reactivex.Observable;

interface WeatherRepository {
    Observable<CurrentWeather> getWeather(String cityName);

    Observable<CitySuggestion> getCitySuggestion(String cityName);

    void setCurrentCity(String city);

    String getCurrentCity();
}
