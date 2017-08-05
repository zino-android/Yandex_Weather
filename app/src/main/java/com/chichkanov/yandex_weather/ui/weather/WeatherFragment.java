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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.Forecast;
import com.chichkanov.yandex_weather.model.current_weather.CurrentWeather;
import com.chichkanov.yandex_weather.ui.BaseFragment;
import com.chichkanov.yandex_weather.ui.adapter.ForecastAdapter;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WeatherFragment extends BaseFragment implements WeatherView, SwipeRefreshLayout.OnRefreshListener {
    private static final int POSITION_IN_MENU = 0;

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
    @BindView(R.id.tv_weather_latest_update)
    TextView tvLatestUpdate;

    @BindView(R.id.iv_weather_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_weather_wind_icon)
    ImageView windIcon;
    @BindView(R.id.iv_weather_humidity_icon)
    ImageView humidityIcon;

    @BindView(R.id.swipe_refresh_weather)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.rv_forecast)
    RecyclerView rvForecast;

    private ForecastAdapter adapter;

    @InjectPresenter
    WeatherPresenter weatherPresenter;

    @ProvidePresenter
    WeatherPresenter providePresenter() {
        return  App.getComponent().getWeatherPresenter();
    }


    public static WeatherFragment newInstance() {
        return new WeatherFragment();
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
        weatherPresenter.addNavigationManager(new NavigationManager(getFragmentManager(), R.id.content_main));
        menuItemChangeListener.onMenuItemChange(POSITION_IN_MENU);

        rvForecast.setHasFixedSize(true);

        rvForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ForecastAdapter(new ArrayList<>(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        rvForecast.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_weather);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        weatherPresenter.loadCurrentWeather();
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
        tvTemp.setText(getString(R.string.weather_error));
        Toast.makeText(getContext(), R.string.message_no_internet, Toast.LENGTH_SHORT).show();

        windIcon.setVisibility(View.GONE);
        humidityIcon.setVisibility(View.GONE);
    }


    @Override
    public void showWeather(CurrentWeather weather, String lastUpdateDate) {
        windIcon.setVisibility(View.VISIBLE);
        humidityIcon.setVisibility(View.VISIBLE);

        tvTemp.setText(getString(R.string.weather_temperature, (int) weather.getMain().getTemp()));
        tvWind.setText(getString(R.string.weather_wind_speed, (int) weather.getWind().getSpeed()));
        tvHumidity.setText(getString(R.string.weather_humidity, weather.getMain().getHumidity()));
        tvDesc.setText(weather.getWeather().get(0).getDescription());
        tvMinMaxTemp.setText(getString(R.string.weather_temperature_minmax, (int) weather.getMain().getTempMax(), (int) weather.getMain().getTempMin()));
        ivIcon.setImageDrawable(WeatherUtils.chooseIcon(weather.getWeather().get(0).getIcon().substring(0, 2), getContext()));

        tvLatestUpdate.setVisibility(View.VISIBLE);
        tvLatestUpdate.setText(getString(R.string.weather_latest_update_time, lastUpdateDate));
    }

    @Override
    public void showCityName(String cityName) {
        tvCity.setText(cityName);
    }

    @Override
    public void onRefresh() {
        weatherPresenter.loadCurrentWeather();
        weatherPresenter.loadForecastWeather();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_change_city) {
            weatherPresenter.onMenuChangeCityClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showForecast(List<Forecast> forecasts) {
        adapter.setForecasts(forecasts);
    }
}
