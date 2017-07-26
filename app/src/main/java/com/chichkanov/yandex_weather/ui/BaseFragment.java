package com.chichkanov.yandex_weather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.chichkanov.yandex_weather.ui.main.OnMenuItemChangeListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends MvpAppCompatFragment {

    protected OnMenuItemChangeListener menuItemChangeListener;
    protected Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        menuItemChangeListener = (OnMenuItemChangeListener) activity;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
