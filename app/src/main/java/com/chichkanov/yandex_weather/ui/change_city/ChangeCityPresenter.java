package com.chichkanov.yandex_weather.ui.change_city;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


@InjectViewState
public class ChangeCityPresenter extends MvpPresenter<ChangeCityView> {

    private ChangeCityInteractor interactor;

    private NavigationManager navigationManager;


    private Scheduler ioScheduler;
    private Scheduler mainScheduler;
    private final CompositeDisposable disposables = new CompositeDisposable();


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
            Disposable disposable = interactor.getCurrentCity()
                    .subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(city -> {
                        getViewState().showCurrentCity(city.getName());
                    });

            disposables.add(disposable);

        } else {
            getViewState().showCurrentCity(currentInput);
        }
    }

    void loadCitySuggestion(String cityName) {
        Log.i("Presenter", "Loading city suggestions");
        currentInput = cityName;
        if (cityName != null && !cityName.equals("")) {
            getViewState().showClearButton();
            Disposable suggestionDisposable = interactor.getCitySuggestion(cityName)
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
            disposables.add(suggestionDisposable);
        } else {
            getViewState().hideClearButton();
        }
    }



    void onCurrentCityChanged(Prediction prediction) {
        interactor.setCurrentCity(prediction);
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
        Disposable cityNameDisposable = observable
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(mainScheduler)
                .subscribe(this::loadCitySuggestion);
        disposables.add(disposables);
    }


    @Override
    public void detachView(ChangeCityView view) {
        super.detachView(view);
        disposables.clear();
    }
}
