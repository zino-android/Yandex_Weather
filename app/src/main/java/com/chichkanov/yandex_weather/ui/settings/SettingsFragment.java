package com.chichkanov.yandex_weather.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.utils.Settings;

public class SettingsFragment extends PreferenceFragmentCompat {

    private NavigationManager navigationManager;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationManager = new NavigationManager(getFragmentManager(), R.id.content_main);
        Preference prefs = getPreferenceManager().findPreference(Settings.CURRENT_CITY_KEY);
        prefs.setSummary(prefs.getSharedPreferences().getString(Settings.CURRENT_CITY_KEY,
                getResources().getString(R.string.default_city)));




    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(Settings.CURRENT_CITY_KEY)) {
            navigationManager.navigateTo(ChangeCityFragment.newInstance());
            return true;
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_settings);
    }


}
