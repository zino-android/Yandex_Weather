package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

interface WeatherRepository {
    Flowable<CurrentWeather> getWeather();

    Flowable<List<Forecast>> getForecasts();

    Maybe<Double> getCurrentTempFromDBbyCityId(int cityId);

    Single<CurrentWeather> getCurrentWeatherFromInternet();

    Single<List<Forecast>> getForecastFromInternet();
}
