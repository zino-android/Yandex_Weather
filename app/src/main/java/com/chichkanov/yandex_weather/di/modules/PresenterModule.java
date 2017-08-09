package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityPresenter;
import com.chichkanov.yandex_weather.ui.main.MainPresenter;
import com.chichkanov.yandex_weather.ui.weather.WeatherPresenter;
import com.chichkanov.yandex_weather.utils.Settings;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class PresenterModule {

    @Provides
    ChangeCityPresenter provideChangeCityPresenter(ChangeCityInteractor interactor) {
        return new ChangeCityPresenter(interactor, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    WeatherPresenter provideWeatherPresenter(WeatherInteractorImpl interactor, ChangeCityInteractor cityInteractor, Settings settings) {
        return new WeatherPresenter(interactor, cityInteractor, settings,  Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    MainPresenter provideMainPresenter(ChangeCityInteractor changeCityInteractor, WeatherInteractorImpl weatherInteractor) {
        return new MainPresenter(changeCityInteractor, weatherInteractor);
    }
}
