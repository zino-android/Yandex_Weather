package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.CurrentWeather;

import io.reactivex.Observable;

interface WeatherInteractor {
    Observable<CurrentWeather> getWeather(String cityName);
}
