package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.repository.RepositoryImpl;

import javax.inject.Inject;

import io.reactivex.Observable;

public class InteractorImpl implements Interactor {

    @Inject
    RepositoryImpl repository;

    public InteractorImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CurrentWeather> getWeather(String cityName) {
        return repository.getWeather(cityName);
    }
}
