package com.chichkanov.yandex_weather.ui.change_city;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import javax.inject.Inject;


@InjectViewState
public class ChangeCityPresenter extends MvpPresenter<ChangeCityView> {

    private ChangeCityInteractor interactor;

    private NavigationManager navigationManager;

    private Disposable suggestionSubscription;
    private Disposable cityNameSubscription;

    private Scheduler ioScheduler;
    private Scheduler mainScheduler;


    private String currentInput;

    @Inject
    public ChangeCityPresenter(ChangeCityInteractor interactor, Scheduler io, Scheduler main) {
        this.interactor = interactor;
        this.ioScheduler = io;
        this.mainScheduler = main;
    }

    @Override
    public void attachView(ChangeCityView view) {
        super.attachView(view);
        if (currentInput == null) {
            String city = interactor.getCurrentCity();
            getViewState().showCurrentCity(city);
        } else {
            getViewState().showCurrentCity(currentInput);
        }
    }

    void loadCitySuggestion(String cityName) {
        Log.i("Presenter", "Loading city suggestions");
        currentInput = cityName;
        if (cityName != null && !cityName.equals("")) {
            getViewState().showClearButton();
            suggestionSubscription = interactor.getCitySuggestion(cityName)
                    .map(CitySuggestion::getPredictions)
                    .subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(response -> {
                        Log.i("Presenter", "Success loading suggestion");
                        getViewState().showSuggestions(response);
                        getViewState().showSuggestionList();
                    }, throwable -> {
                        Log.i("Presenter", "Error loading");
                        Log.i("onError", throwable.toString());
                        getViewState().showError();
                    });
        } else {
            getViewState().hideClearButton();
        }
    }



    void onCurrentCityChanged(String city) {
        interactor.setCurrentCity(city);
        showWeatherFragment();
    }


    void addNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    private void showWeatherFragment() {
        if (navigationManager != null) {
            navigationManager.navigateTo(WeatherFragment.newInstance());
        }
    }

    void onClearClick() {
        getViewState().clearInput();
        getViewState().hideSuggestionList();
    }

    void setObservable(Observable<CharSequence> observable) {
        cityNameSubscription = observable
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(mainScheduler)
                .subscribe(this::loadCitySuggestion);
    }


    @Override
    public void detachView(ChangeCityView view) {
        super.detachView(view);
        if (suggestionSubscription != null) {
            suggestionSubscription.dispose();
        }

        if (cityNameSubscription != null) {
            cityNameSubscription.dispose();
        }
    }
}
