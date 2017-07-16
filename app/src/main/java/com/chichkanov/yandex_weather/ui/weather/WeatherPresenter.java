package com.chichkanov.yandex_weather.ui.weather;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.interactor.InteractorImpl;
import com.chichkanov.yandex_weather.utils.IOtools;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    @Inject
    InteractorImpl interactor;

    WeatherPresenter() {
        App.getComponent().inject(this);
    }

    void loadWeather(String cityName) {
        if (IOtools.getCurrentWeather() != null)
            getViewState().showWeather(IOtools.getCurrentWeather());
        getViewState().showLoading();
        interactor.getWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(f -> {
                    getViewState().hideLoading();
                    if (IOtools.getCurrentWeather() == null) getViewState().showError();
                })
                .filter(f -> f != null)
                .subscribe(response -> {
                    IOtools.saveCurrentWeather(response);
                    getViewState().hideLoading();
                    getViewState().showWeather(response);
                });
    }

    @Override
    public void detachView(WeatherView view) {
        super.detachView(view);
    }
}
