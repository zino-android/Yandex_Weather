package com.chichkanov.yandex_weather.interactor;

import android.util.Log;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.repository.CityRepository;
import com.chichkanov.yandex_weather.repository.CityRepositoryImp;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;

import javax.inject.Inject;

import io.reactivex.Observable;


public class ChangeCityInteractorImp implements ChangeCityInteractor {

    @Inject
    CityRepositoryImp repository;

    public ChangeCityInteractorImp() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CitySuggestion> getCitySuggestion(String cityName) {
        return repository.getCitySuggestion(cityName);
    }

    @Override
    public void setCurrentCity(String city) {
        repository.setCurrentCity(city);
    }

    @Override
    public String getCurrentCity() {
        Log.i("kkk", "getCurrentCity: " + (repository == null));
        return repository.getCurrentCity();
    }
}
