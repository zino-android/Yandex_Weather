package com.chichkanov.yandex_weather.ui.weather;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    @Inject
    WeatherInteractorImpl interactor;

    @Inject
    Settings settings;

    @Inject
    IOtools iotools;

    private Disposable weatherSubscription;

    WeatherPresenter() {
        App.getComponent().inject(this);
    }

    void loadWeather(String cityName) {
        getViewState().showLoading();
        Log.i("Presenter", "Loading weather");
        weatherSubscription = interactor.getWeather(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(response -> {
                    Log.i("Presenter", "Success");

                    getViewState().hideLoading();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
                    String formattedDate = dateFormat.format(new Date(settings.getLastUpdateTime()));

                    getViewState().showWeather(response, formattedDate);
                }, throwable -> {
                    Log.i("Presenter", "Error loading");
                    Log.i("onError", throwable.toString());
                    getViewState().hideLoading();
                    if (iotools.getCurrentWeather() == null) getViewState().showError();
                });
    }

    @Override
    public void detachView(WeatherView view) {
        super.detachView(view);
        weatherSubscription.dispose();
    }
}
