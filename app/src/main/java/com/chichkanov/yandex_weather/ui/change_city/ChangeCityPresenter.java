package com.chichkanov.yandex_weather.ui.change_city;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.interactor.ChangeCityInteractor;
import com.chichkanov.yandex_weather.model.places.CitySuggestion;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ChangeCityPresenter extends MvpPresenter<ChangeCityView> {

    @Inject
    ChangeCityInteractor interactor;

    private Disposable suggestionSubscription;

    ChangeCityPresenter() {
        App.getComponent().inject(this);
    }

    void loadCitySuggestion(String cityName) {
        //getViewState().showLoading();
        Log.i("Presenter", "Loading city suggestions");
        suggestionSubscription = interactor.getCitySuggestion(cityName)
                .map(CitySuggestion::getPredictions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(response -> {
                    Log.i("Presenter", "Success");

                    getViewState().showSuggestions(response);
                }, throwable -> {
                    throwable.printStackTrace();
                });
    }

    void OnCurrentCityChanged(String city) {
        interactor.setCurrentCity(city);
    }

    @Override
    public void detachView(ChangeCityView view) {
        super.detachView(view);
        suggestionSubscription.dispose();
    }
}
