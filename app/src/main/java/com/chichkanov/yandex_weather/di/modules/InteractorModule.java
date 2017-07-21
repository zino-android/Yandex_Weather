package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Singleton
    @Provides
    WeatherInteractorImpl provideInteractor() {
        return new WeatherInteractorImpl();
    }
}
