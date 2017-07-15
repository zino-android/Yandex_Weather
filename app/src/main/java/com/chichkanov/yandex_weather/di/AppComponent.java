package com.chichkanov.yandex_weather.di;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.background.AutoUpdateJob;
import com.chichkanov.yandex_weather.di.modules.InteractorModule;
import com.chichkanov.yandex_weather.di.modules.NetworkModule;
import com.chichkanov.yandex_weather.di.modules.RepositoryModule;
import com.chichkanov.yandex_weather.interactor.InteractorImpl;
import com.chichkanov.yandex_weather.repository.RepositoryImpl;
import com.chichkanov.yandex_weather.ui.weather.WeatherPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, InteractorModule.class})
public interface AppComponent {
    void inject(App app);
    void inject(RepositoryImpl repository);
    void inject(InteractorImpl interactor);
    void inject(WeatherPresenter presenter);
    void inject(AutoUpdateJob autoUpdateJob);
}
