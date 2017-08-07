package com.chichkanov.yandex_weather.ui.weather;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.utils.Settings;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherView> {

    private WeatherInteractorImpl interactor;
    private ChangeCityInteractor cityInteractor;

    private Settings settings;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private NavigationManager navigationManager;

    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    @Inject
    public WeatherPresenter(WeatherInteractorImpl interactor, ChangeCityInteractor changeCityInteractor,
                            Settings settings,  Scheduler io, Scheduler main) {
        this.interactor = interactor;
        this.cityInteractor = changeCityInteractor;
        this.settings = settings;
        this.ioScheduler = io;
        this.mainScheduler = main;
    }

    void loadCurrentWeather() {
        getViewState().showLoading();

        Disposable weatherDisposable = cityInteractor.getCurrentCity()
                .toFlowable()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .doOnNext(city -> {
                    getViewState().showCityName(city.getName());
                })
                .observeOn(ioScheduler)
                .flatMap(city -> interactor.getWeather(city.getDescription()))
                .observeOn(mainScheduler, false)
                .subscribe(response -> {
                    Log.i("Presenter", "Success");

                    getViewState().hideLoading();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
                    String formattedDate = dateFormat.format(new Date(settings.getLastUpdateTime()));

                    getViewState().showWeather(response, formattedDate);

                }, throwable -> {
                    Log.i("Presenter", "Error loading");
                    Log.i("onError", throwable.toString());
                    throwable.printStackTrace();
                    getViewState().hideLoading();
                    getViewState().showError();
                });



        Log.i("Presenter", "Loading weather");

        disposables.add(weatherDisposable);

    }

    public void loadForecastWeather() {
        Disposable forecastDisposable = cityInteractor.getCurrentCity()
                .toFlowable()
                .flatMap(city -> interactor.getForecasts(city.getDescription()))
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
        disposables.add(forecastDisposable);
    }

    @Override
    public void detachView(WeatherView view) {
        super.detachView(view);
        disposables.clear();
    }

    void addNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    void onMenuChangeCityClick() {
        if (navigationManager != null) {
            navigationManager.navigateTo(ChangeCityFragment.newInstance());
        }
    }

    void onRefresh() {
        loadCurrentWeather();
        loadForecastWeather();
    }
}
