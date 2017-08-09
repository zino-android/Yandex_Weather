package com.chichkanov.yandex_weather.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.ui.about.AboutFragment;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.main.OnDrawerEnabled;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.utils.Settings;

public class SettingsFragment extends PreferenceFragmentCompat {

    private NavigationManager navigationManager;
    private OnDrawerEnabled onDrawerEnabled;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDrawerEnabled = (OnDrawerEnabled) activity;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onDrawerEnabled.setDrawerEnabled(false);
        navigationManager = new NavigationManager(getFragmentManager(), R.id.content_main);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(Settings.ABOUT_KEY)) {
            navigationManager.navigateToAndAddBackStack(AboutFragment.newInstance());
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
