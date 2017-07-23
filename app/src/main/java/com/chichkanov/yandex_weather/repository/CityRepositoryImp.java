package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.PlacesApi;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.Settings;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import javax.inject.Inject;

import io.reactivex.Observable;


public class CityRepositoryImp implements CityRepository {

    @Inject
    PlacesApi placesApi;

    @Inject
    Settings settings;

    public CityRepositoryImp() {
        App.getComponent().inject(this);
    }

    @Override
    public Observable<CitySuggestion> getCitySuggestion(String cityName) {
        String locale = WeatherUtils.getLocale();
        Observable<CitySuggestion> citySuggestion = placesApi
                .getCitySuggest(cityName, locale, Constants.PLACES_API_KEY);

        return citySuggestion;
    }

    @Override
    public void setCurrentCity(String city) {
        settings.setCurrentCity(city);
    }

    @Override
    public String getCurrentCity() {
        return settings.getCurrentCity();
    }
}
