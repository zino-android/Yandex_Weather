package com.chichkanov.yandex_weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import javax.inject.Singleton;

@Singleton
public class Settings {

    private Context context;

    public Settings(Context context) {
        this.context = context;
    }

    public long getAutoRefreshTime() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String updateValue = pref.getString("refresh_update", "0");
        return updateValue.equals("0") ? 0 : Long.valueOf(updateValue);
    }
}
