package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    WeatherRepositoryImpl provideRepository() {
        return new WeatherRepositoryImpl();
    }
}
