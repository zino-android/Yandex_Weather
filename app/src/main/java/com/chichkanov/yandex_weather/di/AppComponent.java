package com.chichkanov.yandex_weather.di;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.background.AutoUpdateJob;
import com.chichkanov.yandex_weather.db.WeatherDatabase;
import com.chichkanov.yandex_weather.di.modules.ApplicationModule;
import com.chichkanov.yandex_weather.di.modules.DbModule;
import com.chichkanov.yandex_weather.di.modules.InteractorModule;
import com.chichkanov.yandex_weather.di.modules.NetworkModule;
import com.chichkanov.yandex_weather.di.modules.PresenterModule;
import com.chichkanov.yandex_weather.di.modules.RepositoryModule;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractorImpl;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.repository.CityRepositoryImpl;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityPresenter;
import com.chichkanov.yandex_weather.ui.favorite_cities.FavoriteCitiesPresenter;
import com.chichkanov.yandex_weather.ui.weather.WeatherPresenter;
import com.chichkanov.yandex_weather.utils.Settings;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, InteractorModule.class, ApplicationModule.class,
        PresenterModule.class, DbModule.class})

public interface AppComponent {
    void inject(App app);

    void inject(WeatherRepositoryImpl repository);

    void inject(CityRepositoryImpl repository);

    void inject(WeatherInteractorImpl interactor);

    void inject(ChangeCityInteractorImpl interactor);

    void inject(WeatherPresenter presenter);

    void inject(ChangeCityPresenter presenter);

    void inject(AutoUpdateJob autoUpdateJob);

    void inject(Settings settings);

    ChangeCityPresenter getChangeCityPresenter();

    WeatherPresenter getWeatherPresenter();

    FavoriteCitiesPresenter getFavoritesPresenter();

    void inject(WeatherDatabase weatherDatabase);
}
