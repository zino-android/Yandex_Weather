package com.chichkanov.yandex_weather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_weather_city)
    TextView tvCity;
    @BindView(R.id.tv_weather_temp)
    TextView tvTemp;
    @BindView(R.id.tv_weather_wind_speed)
    TextView tvWind;
    @BindView(R.id.tv_weather_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_weather_description)
    TextView tvDesc;
    @BindView(R.id.tv_weather_temp_minmax)
    TextView tvMinMaxTemp;

    @BindView(R.id.iv_weather_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_weather_wind_icon)
    ImageView windIcon;
    @BindView(R.id.iv_weather_humidity_icon)
    ImageView humidityIcon;

    @BindView(R.id.swipe_refresh_weather)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectPresenter
    WeatherPresenter weatherPresenter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_weather);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        weatherPresenter.loadWeather("Москва");
    }

    @Override
    public void showLoading() {
        windIcon.setVisibility(View.GONE);
        humidityIcon.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        windIcon.setVisibility(View.GONE);
        humidityIcon.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(CurrentWeather weather) {
        windIcon.setVisibility(View.VISIBLE);
        humidityIcon.setVisibility(View.VISIBLE);

        tvCity.setText(weather.getName());
        tvTemp.setText(getString(R.string.weather_temperature, (int) weather.getMain().getTemp()));
        tvWind.setText(getString(R.string.weather_wind_speed, (int) weather.getWind().getSpeed()));
        tvHumidity.setText(getString(R.string.weather_humidity, weather.getMain().getHumidity()));
        tvDesc.setText(weather.getWeather().get(0).getDescription());
        tvMinMaxTemp.setText(getString(R.string.weather_temperature_minmax, (int) weather.getMain().getTempMax(), (int) weather.getMain().getTempMin()));
        ivIcon.setImageDrawable(WeatherUtils.chooseIcon(weather.getWeather().get(0).getIcon().substring(0, 2), getContext()));
    }

    @Override
    public void onRefresh() {
        weatherPresenter.loadWeather("Москва");
    }
}
