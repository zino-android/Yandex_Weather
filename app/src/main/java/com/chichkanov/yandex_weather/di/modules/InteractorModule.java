package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.interactor.InteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InteractorModule {

    @Singleton
    @Provides
    InteractorImpl provideInteractor() {
        return new InteractorImpl();
    }
}
