package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;

import java.util.List;

import io.reactivex.Observable;

interface WeatherRepository {
    Observable<CurrentWeather> getWeather(String cityName);

    Observable<List<Forecast>> getForecasts(String cityName);
}
