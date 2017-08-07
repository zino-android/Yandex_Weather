package com.chichkanov.yandex_weather.repository;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.PlacesApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


public class CityRepositoryImpl implements CityRepository {

    @Inject
    PlacesApi placesApi;

    @Inject
    WeatherDatabase database;

    public CityRepositoryImpl() {
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
    public void setCurrentCity(Prediction prediction) {
        City city = new City();
        city.setPlacesId(prediction.getId());
        city.setSelected(true);
        city.setDescription(prediction.getDescription());
        city.setName(prediction.getStructuredFormatting().getMainText());
        Single.fromCallable(() -> database.cityDao().insertCity(city))
                .subscribeOn(Schedulers.io())
                .subscribe(i -> {database.cityDao().updateSelectedCity(i);});

    }

    @Override
    public Maybe<City> getCurrentCity() {
        return database.cityDao().loadCurrentCity();
    }
}
