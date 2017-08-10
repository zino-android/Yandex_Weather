package com.chichkanov.yandex_weather.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chichkanov.yandex_weather.BuildConfig;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.ui.BaseFragment;

import butterknife.BindView;

public class AboutFragment extends BaseFragment {
    @BindView(R.id.version_text_view)
    TextView versionTextView;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onDrawerEnabled.setDrawerEnabled(false);
        String version = BuildConfig.VERSION_NAME;
        versionTextView.setText(String.format(getResources().getString(R.string.version), version));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.menu_about);
    }
}
