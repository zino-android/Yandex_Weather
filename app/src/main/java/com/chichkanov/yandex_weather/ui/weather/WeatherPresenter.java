package com.chichkanov.yandex_weather.ui.weather;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.chichkanov.yandex_weather.utils.Settings;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private WeatherInteractorImpl interactor;

    private Settings settings;

    private IOtools iotools;

    private Disposable weatherSubscription;
    private Disposable forecastSubscription;

    private NavigationManager navigationManager;

    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    @Inject
    public WeatherPresenter(WeatherInteractorImpl interactor, Settings settings, IOtools iotools, Scheduler io, Scheduler main) {
        this.interactor = interactor;
        this.settings = settings;
        this.iotools = iotools;
        this.ioScheduler = io;
        this.mainScheduler = main;
    }

    void loadCurrentWeather() {
        getViewState().showLoading();
        String cityName = settings.getCurrentCity();
        Log.i("Presenter", "Loading weather");
        weatherSubscription = interactor.getWeather(cityName)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler, true)
                .subscribe(response -> {
                    Log.i("Presenter", "Success");

                    getViewState().hideLoading();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
                    String formattedDate = dateFormat.format(new Date(settings.getLastUpdateTime()));

                    getViewState().showWeather(response, formattedDate);
                    getViewState().showCityName(settings.getCurrentCity());
                }, throwable -> {
                    Log.i("Presenter", "Error loading");
                    Log.i("onError", throwable.toString());
                    getViewState().hideLoading();
                    if (iotools.getCurrentWeather() == null) getViewState().showError();
                });


    }

    public void loadForecastWeather() {

        String cityName = settings.getCurrentCity();

        forecastSubscription = interactor.getForecasts(cityName)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler, true)
                .subscribe(forecasts -> {
                    Log.i("Presenter", "Success");

                    getViewState().hideLoading();

                    getViewState().showForecast(forecasts);
                }, throwable -> {
                    Log.i("Presenter", "Error loading");
                    Log.i("onError", throwable.toString());
                    throwable.printStackTrace();
                    getViewState().hideLoading();
                    getViewState().showError();
                });
    }

    @Override
    public void detachView(WeatherView view) {
        super.detachView(view);
        if (weatherSubscription != null) {
            weatherSubscription.dispose();
        }
    }

    void addNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    void onMenuChangeCityClick() {
        if (navigationManager != null) {
            navigationManager.navigateTo(ChangeCityFragment.newInstance());
        }
    }
}
