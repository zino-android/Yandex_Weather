package com.chichkanov.yandex_weather.repository;

import android.util.Log;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.api.PlacesApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.model.City;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
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
    public Flowable<City> getCurrentCity() {
        return database.cityDao().loadCurrentCity().toFlowable();
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
                .subscribe(i -> {
                    database.cityDao().updateSelectedCity(i);
                });

    }

    @Override
    public Flowable<List<City>> getCities() {
        return database.cityDao().getCities();
    }

    @Override
    public void setCitySelected(int cityId) {
        Log.i("kkkkkk", "setCitySelected: " + cityId);
        Completable.fromAction(() -> {

            database.beginTransaction();
            try {
                database.cityDao().selectCity(cityId);
                database.cityDao().unSelectOldCity(cityId);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }


        }).subscribeOn(Schedulers.io()).subscribe();

    }
}
