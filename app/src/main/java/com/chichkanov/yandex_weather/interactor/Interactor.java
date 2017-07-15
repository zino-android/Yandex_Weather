package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.CurrentWeather;

import io.reactivex.Observable;

interface Interactor {
    Observable<CurrentWeather> getWeather(String cityName);
}
