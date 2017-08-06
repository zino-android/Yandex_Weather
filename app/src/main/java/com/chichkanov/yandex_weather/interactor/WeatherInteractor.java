package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

import io.reactivex.Flowable;

interface WeatherInteractor {
    Flowable<CurrentWeather> getWeather(String cityName);

    Flowable<List<Forecast>> getForecasts(String cityName);
}
