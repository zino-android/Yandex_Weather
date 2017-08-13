package com.chichkanov.yandex_weather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CurrentWeather;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.ui.BaseFragment;
import com.chichkanov.yandex_weather.ui.adapter.ForecastAdapter;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WeatherFragment extends BaseFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_refresh_weather)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_forecast)
    RecyclerView rvForecast;

    @BindView(R.id.error_relative_layout)
    RelativeLayout errorRelativeLayout;
    @BindView(R.id.city_not_selected_relative_layout)
    RelativeLayout cityNotSelectedRelativeLayout;
    @InjectPresenter
    WeatherPresenter weatherPresenter;
    private ForecastAdapter adapter;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @ProvidePresenter
    WeatherPresenter providePresenter() {
        return App.getComponent().getWeatherPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onDrawerEnabled.setDrawerEnabled(true);
        weatherPresenter.addNavigationManager(new NavigationManager(getFragmentManager(), R.id.content_main));

        rvForecast.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvForecast.setLayoutManager(layoutManager);
        adapter = new ForecastAdapter(getContext(), new ArrayList<>());
        rvForecast.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showError() {
        errorRelativeLayout.setVisibility(View.VISIBLE);
        rvForecast.setVisibility(View.INVISIBLE);
        cityNotSelectedRelativeLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showWeather(CurrentWeather weather, String lastUpdateDate) {
        adapter.setWeather(weather);
        rvForecast.setVisibility(View.VISIBLE);
        errorRelativeLayout.setVisibility(View.INVISIBLE);
        cityNotSelectedRelativeLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showCityName(String cityName) {
        getActivity().setTitle(cityName);
    }

    @Override
    public void onRefresh() {
        weatherPresenter.onRefresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_settings) {
            weatherPresenter.onMenuSettingsClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showForecast(List<Forecast> forecasts) {
        adapter.setForecasts(forecasts);
        rvForecast.smoothScrollToPosition(0);
    }

    @Override
    public void hideRecyclerView() {
        rvForecast.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRecyclerView() {
        rvForecast.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddCityLayout() {
        errorRelativeLayout.setVisibility(View.INVISIBLE);
        cityNotSelectedRelativeLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.add_city_button)
    void onAddCityClicked() {
        weatherPresenter.onMenuChangeCityClick();
    }
}
