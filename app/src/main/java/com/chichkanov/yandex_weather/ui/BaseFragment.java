package com.chichkanov.yandex_weather.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.chichkanov.yandex_weather.ui.main.OnDrawerEnabled;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends MvpAppCompatFragment {

    protected OnDrawerEnabled onDrawerEnabled;
    protected Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDrawerEnabled = (OnDrawerEnabled) activity;

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
