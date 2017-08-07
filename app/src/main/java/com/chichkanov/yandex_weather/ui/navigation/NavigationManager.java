package com.chichkanov.yandex_weather.ui.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class NavigationManager {

    private FragmentManager fragmentManager;
    private int containerId;

    public NavigationManager(FragmentManager fragmentManager, int containerId){
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    public void navigateTo(Fragment fragment){
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public void navigateToAndAddBackStack(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }
}
