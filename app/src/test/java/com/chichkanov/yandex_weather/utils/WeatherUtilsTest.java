package com.chichkanov.yandex_weather.utils;

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



}