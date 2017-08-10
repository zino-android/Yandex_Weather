package com.chichkanov.yandex_weather.ui.favorite_cities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.ui.BaseFragment;
import com.chichkanov.yandex_weather.ui.adapter.CityAdapter;
import com.chichkanov.yandex_weather.ui.main.CityItemTouchHelper;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FavoriteCitiesFragment extends BaseFragment implements FavoriteCitiesView {

    @BindView(R.id.rv_cities)
    RecyclerView rvCities;

    private CityAdapter adapter;

    @InjectPresenter
    FavoriteCitiesPresenter favoritesPresenter;

    @ProvidePresenter
    FavoriteCitiesPresenter providePresenter() {
        return App.getComponent().getFavoritesPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_cities, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCities.setHasFixedSize(true);
        favoritesPresenter.addNavigationManager(new NavigationManager(getFragmentManager(), R.id.content_main));

        rvCities.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CityAdapter(getContext(), new ArrayList<>(), city -> {
            favoritesPresenter.onCitySelectedChanged(city.getCityId());
            onDrawerEnabled.closeDrawer();
        }, () -> {
            favoritesPresenter.showChangeCityFragment();
        });
        rvCities.setAdapter(adapter);
        favoritesPresenter.loadCities();

        CityItemTouchHelper cityItemTouchHelper = new CityItemTouchHelper(i -> {

            favoritesPresenter.deleteCityById(adapter.getCities().get(i));
        }, adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(cityItemTouchHelper);

        itemTouchHelper.attachToRecyclerView(rvCities);

    }

    @Override
    public void showCities(List<CityMenu> cities) {
        adapter.setCities(cities);
    }

}
