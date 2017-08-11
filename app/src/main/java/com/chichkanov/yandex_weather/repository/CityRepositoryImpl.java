package com.chichkanov.yandex_weather.repository;

import android.support.annotation.NonNull;
import android.util.Log;

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
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


public class CityRepositoryImpl implements CityRepository {
    private PlacesApi placesApi;
    private WeatherDatabase database;
    private Scheduler ioScheduler;

    @Inject
    public CityRepositoryImpl(PlacesApi placesApi, WeatherDatabase database, Scheduler ioScheduler) {
        this.placesApi = placesApi;
        this.database = database;
        this.ioScheduler = ioScheduler;
    }

    @Override
    public Observable<CitySuggestion> getCitySuggestion(@NonNull String cityName) {
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
    public void setCurrentCity(@NonNull Prediction prediction) {
        City city = new City();
        city.setPlacesId(prediction.getId());
        city.setSelected(true);
        city.setDescription(prediction.getDescription());
        city.setName(prediction.getStructuredFormatting().getMainText());
        Completable.fromAction(() -> {
            database.beginTransaction();
            try {
                long i = database.cityDao().insertCity(city);
                database.cityDao().updateSelectedCity(i);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        })
                .subscribeOn(ioScheduler)
                .subscribe();

    }

    @Override
    public Flowable<List<City>> getCities() {
        return database.cityDao().getCities();
    }

    @Override
    public void setCitySelected(int cityId) {
        Completable.fromAction(() -> {
            database.beginTransaction();
            try {
                database.cityDao().selectCity(cityId);
                database.cityDao().unSelectOldCity(cityId);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }


        }).subscribeOn(ioScheduler).subscribe();

    }

    @Override
    public void deleteCityById(int cityId) {
        Completable.fromAction(() -> {
            database.cityDao().deleteCityById(cityId);
            database.currentWeatherDao().deleteCurrentWeatherByCityId(cityId);
            database.forecastDao().deleteForecastsByCityId(cityId);
        }).subscribeOn(ioScheduler).subscribe();
    }
}
