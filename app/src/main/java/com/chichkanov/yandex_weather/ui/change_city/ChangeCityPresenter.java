package com.chichkanov.yandex_weather.ui.change_city;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ChangeCityPresenter extends MvpPresenter<ChangeCityView> {

    @Inject
    ChangeCityInteractor interactor;

    private NavigationManager navigationManager;

    private Disposable suggestionSubscription;


    private String currentInput;

    ChangeCityPresenter() {
        App.getComponent().inject(this);
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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), true)
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



    void OnCurrentCityChanged(String city) {
        interactor.setCurrentCity(city);
        showWeatherFragment();
    }


    void addNavigationManager(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
    }

    private void showWeatherFragment() {
        navigationManager.navigateTo(WeatherFragment.newInstance());
    }

    void onClearClick() {
        getViewState().clearInput();
        getViewState().hideSuggestionList();
    }


    @Override
    public void detachView(ChangeCityView view) {
        super.detachView(view);
        suggestionSubscription.dispose();
    }
}
