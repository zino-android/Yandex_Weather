package com.chichkanov.yandex_weather.ui.main;

import com.chichkanov.yandex_weather.ui.about.AboutFragment;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.chichkanov.yandex_weather.ui.settings.SettingsFragment;
import com.chichkanov.yandex_weather.ui.weather.WeatherFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    @Mock
    NavigationManager navigationManager;

    MainPresenter presenter;


    @Before
    public void setUp() {
        presenter = new MainPresenter();
        presenter.addNavigationManager(navigationManager);
    }


    @Test
    public void testShowWeatherFragment() throws Exception {
        presenter.showWeatherFragment();
        verify(navigationManager, times(1)).navigateTo(any(WeatherFragment.class));
    }

    @Test
    public void testShowSettingsFragment() throws Exception {
        presenter.showSettingsFragment();
        verify(navigationManager, times(1)).navigateTo(any(SettingsFragment.class));
    }

    @Test
    public void testShowAboutFragment() throws Exception {
        presenter.showAboutFragment();
        verify(navigationManager, times(1)).navigateTo(any(AboutFragment.class));
    }

    @Test
    public void testShowChangeCityFragment() throws Exception {
        presenter.showChangeCityFragment();
        verify(navigationManager, times(1)).navigateToAndAddBackStack(any(ChangeCityFragment.class));
    }

}