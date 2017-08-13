package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.api.PlacesApi;
import com.chichkanov.yandex_weather.api.WeatherApi;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.repository.CityRepositoryImpl;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;
import com.chichkanov.yandex_weather.utils.Settings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    WeatherRepositoryImpl provideRepository(WeatherApi weatherApi, Settings settings, WeatherDatabase database) {
        return new WeatherRepositoryImpl(weatherApi, settings, database);
    }

    @Singleton
    @Provides
    CityRepositoryImpl provideCityRepository(PlacesApi placesApi, WeatherDatabase database) {
        return new CityRepositoryImpl(placesApi, database, Schedulers.io());
    }
}
