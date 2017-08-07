package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.repository.CityRepositoryImpl;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;



public class ChangeCityInteractorImpl implements ChangeCityInteractor {

    @Inject
    CityRepositoryImpl repository;

    public ChangeCityInteractorImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CitySuggestion> getCitySuggestion(String cityName) {
        return repository.getCitySuggestion(cityName);
    }

    @Override
    public void setCurrentCity(Prediction city) {
        repository.setCurrentCity(city);
    }

    @Override
    public Maybe<City> getCurrentCity() {
        return repository.getCurrentCity();
    }
}
