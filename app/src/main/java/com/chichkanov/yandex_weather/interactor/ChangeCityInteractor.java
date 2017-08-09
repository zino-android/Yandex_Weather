package com.chichkanov.yandex_weather.interactor;

import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;


public interface ChangeCityInteractor {
    Observable<CitySuggestion> getCitySuggestion(String cityName);

    void setCurrentCity(Prediction city);

    Flowable<City> getCurrentCity();

    Flowable<List<City>> getCities();

    void setCitySelected(int cityId);

    void deleteCityById(int cityId);
}
