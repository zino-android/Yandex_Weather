package com.chichkanov.yandex_weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener {

    private static final int WEATHER_ID = 0;
    private static final int SETTINGS_ID = 1;
    private static final int ABOUT_ID = 2;
    private static final String CURRENT_MENU_ITEM_SELECTED = "current_menu_item_selected";

    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavigation();
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setTitle(savedInstanceState.getString(CURRENT_MENU_ITEM_SELECTED));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_MENU_ITEM_SELECTED, String.valueOf(getTitle()));
    }

    private void initNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(WEATHER_ID)
                .withName(R.string.menu_weather)
                .withIcon(R.drawable.ic_menu_weather);

        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withIdentifier(SETTINGS_ID)
                .withName(R.string.menu_settings)
                .withIcon(R.drawable.ic_menu_settings);

        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withIdentifier(ABOUT_ID)
                .withName(R.string.menu_about)
                .withIcon(R.drawable.ic_menu_about);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)

                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3
                )
                .withOnDrawerItemClickListener(this)
                .build();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case WEATHER_ID: {
                setTitle(R.string.menu_weather);
                WeatherFragment fragment = WeatherFragment.newInstance();
                showFragment(fragment);
                drawer.closeDrawer();
                return true;
            }
            case SETTINGS_ID: {
                setTitle(R.string.menu_settings);
                SettingsFragment fragment = SettingsFragment.newInstance();
                showFragment(fragment);
                drawer.closeDrawer();
                return true;
            }
            case ABOUT_ID: {
                setTitle(R.string.menu_about);
                AboutFragment fragment = AboutFragment.newInstance();
                showFragment(fragment);
                drawer.closeDrawer();
                return true;
            }
        }
        return false;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft = ft.replace(R.id.content_main, fragment);
        ft.commit();
    }
}
