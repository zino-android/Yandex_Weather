package com.chichkanov.yandex_weather.di.modules;

import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Алексей on 28.07.2017.
 */
@Module
public class PresenterModule {

    @Singleton
    @Provides
    ChangeCityPresenter provideChangeCityPresenter(ChangeCityInteractor interactor) {
        return new ChangeCityPresenter(interactor, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
