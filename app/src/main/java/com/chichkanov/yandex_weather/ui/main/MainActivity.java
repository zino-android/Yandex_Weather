package com.chichkanov.yandex_weather.ui.main;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.ui.favorite_cities.FavoriteCitiesFragment;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView, OnDrawerEnabled {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ActionBarDrawerToggle toggle;

    private boolean isTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        isTwoPane = findViewById(R.id.container) != null;

        if (isTwoPane) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new FavoriteCitiesFragment()).commit();
        }

        initNavigation();
        mainPresenter.addNavigationManager(new NavigationManager(getSupportFragmentManager(), R.id.content_main));

        if (savedInstanceState == null) {
            mainPresenter.showWeatherFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigation() {
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setDrawerEnabled(boolean isEnabled) {
        int lockMode = isEnabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        if (isEnabled) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(null);
        } else {
            toggle.setDrawerIndicatorEnabled(false);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toggle.setToolbarNavigationClickListener(v -> onBackPressed());
        }
        if (isTwoPane) {
            toggle.setDrawerIndicatorEnabled(false);
        }
    }

    @Override
    public void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}
