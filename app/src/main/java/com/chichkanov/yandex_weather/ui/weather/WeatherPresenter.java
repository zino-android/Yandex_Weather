package com.chichkanov.yandex_weather.ui.weather;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.utils.IOtools;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    @Inject
    WeatherInteractorImpl interactor;

    private Disposable weatherSubscription;

    WeatherPresenter() {
        App.getComponent().inject(this);
    }

    void loadWeather(String cityName) {
        getViewState().showLoading();
        Log.i("Presenter", "Loading weather");
        weatherSubscription = interactor.getWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.i("Presenter", "Success");
                    getViewState().hideLoading();
                    getViewState().showWeather(response);
                }, throwable -> {
                    Log.i("Presenter", "Error loading");
                    getViewState().hideLoading();
                    if (IOtools.getCurrentWeather() == null) getViewState().showError();
                });
    }

    @Override
    public void detachView(WeatherView view) {
        super.detachView(view);
        weatherSubscription.dispose();
    }
}
