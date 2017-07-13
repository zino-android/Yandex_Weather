package com.chichkanov.yandex_weather.ui.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.chichkanov.yandex_weather.R;

public class WeatherFragment extends Fragment {

    public static WeatherFragment newInstance(){
        return new WeatherFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_weather);
    }

}
