package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.repository.RepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    RepositoryImpl provideRepository(){
        return new RepositoryImpl();
    }
}
