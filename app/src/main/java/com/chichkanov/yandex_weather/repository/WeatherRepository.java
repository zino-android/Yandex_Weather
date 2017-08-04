package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;

import io.reactivex.Observable;

interface WeatherRepository {
    Observable<CurrentWeather> getWeather(String cityName);

    Observable<ForecastResponse> getForecasts(String cityName);
}
