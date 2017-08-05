package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;

import java.util.List;

import io.reactivex.Observable;

interface WeatherInteractor {
    Observable<CurrentWeather> getWeather(String cityName);

    Observable<List<Forecast>> getForecasts(String cityName);
}
