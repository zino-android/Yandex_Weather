package com.chichkanov.yandex_weather.utils;

import android.content.Context;
import android.graphics.Color;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.meteocons_typeface_library.Meteoconcs;

public class WeatherUtils {

    private static final int ICON_SIZE = 80;

    public static IconicsDrawable chooseIcon(String iconId, Context context) {
        switch (iconId) {
            case "01": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_sun).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "02": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_cloud_sun).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "03": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_cloud).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "04": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_cloud).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "09": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_rain).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "10": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_rain).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "11": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_cloud_flash).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "13": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_snow).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
            case "50": {
                return new IconicsDrawable(context).icon(Meteoconcs.Icon.met_mist).color(Color.WHITE).sizeDp(ICON_SIZE);
            }
        }
        return null;
    }
}
