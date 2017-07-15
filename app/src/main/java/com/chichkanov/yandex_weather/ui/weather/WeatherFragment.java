package com.chichkanov.yandex_weather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CurrentWeather;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView {

    @InjectPresenter
    WeatherPresenter weatherPresenter;

    public static WeatherFragment newInstance(){
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_weather);

        weatherPresenter.loadWeather("Москва");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showWeather(CurrentWeather weather) {
        Log.i("Weather", String.valueOf(weather.getMain().getTemp()));
    }
}
