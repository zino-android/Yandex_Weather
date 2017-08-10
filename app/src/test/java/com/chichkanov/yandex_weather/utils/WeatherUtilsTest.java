package com.chichkanov.yandex_weather.utils;

import com.chichkanov.yandex_weather.R;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class WeatherUtilsTest {


    @Test
    public void testGetLocale() throws Exception {
        Locale.setDefault(new Locale("ru", "RU"));
        assertEquals(WeatherUtils.getLocale(), "ru");

        Locale.setDefault(new Locale("en", "EN"));
        assertEquals(WeatherUtils.getLocale(), "en");

        Locale.setDefault(new Locale("es", "ES"));
        assertEquals(WeatherUtils.getLocale(), "en");
    }

    @Test
    public void testFormatTemperature() {
        assertEquals("+27°", WeatherUtils.formatTemperature(27.0));
        assertEquals("-27°", WeatherUtils.formatTemperature(-27.0));
        assertEquals("-27°", WeatherUtils.formatTemperature(-26.8));
        assertEquals("0°", WeatherUtils.formatTemperature(0.0));
    }

    @Test
    public void testGetImageByIdReturnSun() {
        assertEquals(R.drawable.sun, WeatherUtils.getImageIdByName("01d"));
        assertEquals(R.drawable.sun, WeatherUtils.getImageIdByName("01n"));
    }

    @Test
    public void testGetImageByIdReturnCloudSun() {
        assertEquals(R.drawable.cloud_sun, WeatherUtils.getImageIdByName("02d"));
        assertEquals(R.drawable.cloud_sun, WeatherUtils.getImageIdByName("02n"));
    }

    @Test
    public void testGetImageByIdReturnCloud() {
        assertEquals(R.drawable.cloud, WeatherUtils.getImageIdByName("03d"));
        assertEquals(R.drawable.cloud, WeatherUtils.getImageIdByName("03n"));
        assertEquals(R.drawable.cloud, WeatherUtils.getImageIdByName("04d"));
        assertEquals(R.drawable.cloud, WeatherUtils.getImageIdByName("04n"));
    }

    @Test
    public void testGetImageByIdReturnShowerRain() {
        assertEquals(R.drawable.shower_rain, WeatherUtils.getImageIdByName("09d"));
        assertEquals(R.drawable.shower_rain, WeatherUtils.getImageIdByName("09n"));
    }

    @Test
    public void testGetImageByIdReturnRain() {
        assertEquals(R.drawable.rain, WeatherUtils.getImageIdByName("10d"));
        assertEquals(R.drawable.rain, WeatherUtils.getImageIdByName("10n"));
    }

    @Test
    public void testGetImageByIdReturnThunderstorm() {
        assertEquals(R.drawable.thunderstorm, WeatherUtils.getImageIdByName("11d"));
        assertEquals(R.drawable.thunderstorm, WeatherUtils.getImageIdByName("11n"));
    }

    @Test
    public void testGetImageByIdReturnMist() {
        assertEquals(R.drawable.mist, WeatherUtils.getImageIdByName("13d"));
        assertEquals(R.drawable.mist, WeatherUtils.getImageIdByName("13n"));
    }

    @Test
    public void testGetImageByIdReturnDefault() {
        assertEquals(R.drawable.sun, WeatherUtils.getImageIdByName("43624"));
        assertEquals(R.drawable.sun, WeatherUtils.getImageIdByName("15n"));
    }

}