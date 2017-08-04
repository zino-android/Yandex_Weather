package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.chichkanov.yandex_weather.model.forecast.Forecast;
import com.chichkanov.yandex_weather.model.forecast.ForecastResponse;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class WeatherInteractorImpl implements WeatherInteractor {

    @Inject
    WeatherRepositoryImpl repository;

    public WeatherInteractorImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {
        return repository.getWeather(cityName);
    }

    @Override
    public Observable<List<Forecast>> getForecasts(String cityName) {
        return repository.getForecasts(cityName)
                .map(ForecastResponse::getForecasts);
    }
}
