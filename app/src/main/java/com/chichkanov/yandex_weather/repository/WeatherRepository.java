package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

import io.reactivex.Flowable;

interface WeatherRepository {
    Flowable<CurrentWeather> getWeather(String cityName);

    Flowable<List<Forecast>> getForecasts(String cityName);
}
