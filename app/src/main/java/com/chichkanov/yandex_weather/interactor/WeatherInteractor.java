package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface WeatherInteractor {
    Flowable<CurrentWeather> getWeather();

    Single<List<Forecast>> getForecasts();

    Maybe<Double> getCurrentWeatherFromDBbyId(int cityId);

    Single<List<Forecast>> getForecastsFromInternet();

    Single<CurrentWeather> getCurrentWeatherFromInternet();
}
