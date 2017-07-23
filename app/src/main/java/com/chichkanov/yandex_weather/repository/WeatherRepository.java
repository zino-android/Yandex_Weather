package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.CurrentWeather;

import io.reactivex.Observable;

interface WeatherRepository {
    Observable<CurrentWeather> getWeather(String cityName);

}
