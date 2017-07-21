package com.chichkanov.yandex_weather.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.chichkanov.yandex_weather.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_settings);
    }
}
