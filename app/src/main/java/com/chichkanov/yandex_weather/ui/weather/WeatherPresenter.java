package com.chichkanov.yandex_weather.ui.weather;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.interactor.WeatherInteractorImpl;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.settings.SettingsFragment;
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

    private final CompositeDisposable disposables = new CompositeDisposable();
    private WeatherInteractorImpl interactor;
    private ChangeCityInteractor cityInteractor;
    private Settings settings;
    private NavigationManager navigationManager;

    private Scheduler ioScheduler;
    private Scheduler mainScheduler;

    @Inject
    public WeatherPresenter(WeatherInteractorImpl interactor, ChangeCityInteractor changeCityInteractor,
                            Settings settings, Scheduler io, Scheduler main) {
        this.interactor = interactor;
        this.cityInteractor = changeCityInteractor;
        this.settings = settings;
        this.ioScheduler = io;
        this.mainScheduler = main;
    }

    void loadCurrentWeather() {
        getViewState().showLoading();

        Disposable weatherDisposable = interactor.getWeather()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler, false)
                .subscribe(response -> {

                    getViewState().hideLoading();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
                    String formattedDate = dateFormat.format(new Date(settings.getLastUpdateTime()));

                    getViewState().showWeather(response, formattedDate);

                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideLoading();
                    getViewState().showError();
                });


        loadCurrentCity();

        disposables.add(weatherDisposable);

    }

    private void loadCurrentCity() {
        Disposable cityDisposable = cityInteractor.getCurrentCity()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(city -> getViewState().showCityName(city.getName()));
        disposables.add(cityDisposable);

    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        loadForecastWeather();
        loadCurrentWeather();
    }

    public void loadForecastWeather() {
        Disposable forecastDisposable = interactor.getForecasts()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(forecasts -> {
                    getViewState().hideLoading();

                    getViewState().showForecast(forecasts);
                }, throwable -> {
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
            navigationManager.navigateToAndAddBackStack(ChangeCityFragment.newInstance());
        }
    }

    void onMenuSettingsClick() {
        if (navigationManager != null) {
            navigationManager.navigateToAndAddBackStack(SettingsFragment.newInstance());
        }
    }

    void onRefresh() {
        loadWeatherFromInternet();
        loadForecastFromInternet();
    }

    private void loadForecastFromInternet() {
        Disposable forecastDisposable = interactor.getForecastsFromInternet()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(forecasts -> {
                    getViewState().hideLoading();

                    getViewState().showForecast(forecasts);
                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideLoading();
                    getViewState().showError();
                });
        disposables.add(forecastDisposable);
    }

    private void loadWeatherFromInternet() {
        Disposable weatherDisposable = interactor.getCurrentWeatherFromInternet()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(response -> {
                    getViewState().hideLoading();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
                    String formattedDate = dateFormat.format(new Date(settings.getLastUpdateTime()));

                    getViewState().showWeather(response, formattedDate);

                }, throwable -> {
                    throwable.printStackTrace();
                    getViewState().hideLoading();
                    getViewState().showError();
                });


        loadCurrentCity();

        disposables.add(weatherDisposable);

    }

}
