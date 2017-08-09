package com.chichkanov.yandex_weather.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.ui.adapter.CityAdapter;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, MainView, OnDrawerEnabled {

    @InjectPresenter
    MainPresenter mainPresenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv_cities)
    RecyclerView rvCities;
    private ActionBarDrawerToggle toggle;

    private CityAdapter adapter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return  App.getComponent().getMainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initNavigation();
        mainPresenter.addNavigationManager(new NavigationManager(getSupportFragmentManager(), R.id.content_main));

        rvCities.setHasFixedSize(true);

        rvCities.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CityAdapter(getApplicationContext(), new ArrayList<>(), city -> {
            mainPresenter.onCitySelectedChanged(city.getCityId());
            drawerLayout.closeDrawer(Gravity.START);
        });
        rvCities.setAdapter(adapter);
        mainPresenter.loadCities();


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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
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
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(null);
        }
        else {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toggle.setToolbarNavigationClickListener(v -> onBackPressed());
        }
    }

    @Override
    public void showCities(List<CityMenu> cities) {
        adapter.setCities(cities);
    }
}
