package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeatherResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

interface WeatherRepository {
    Flowable<CurrentWeather> getWeather(String cityName);

    Flowable<List<Forecast>> getForecasts(String cityName);
}
