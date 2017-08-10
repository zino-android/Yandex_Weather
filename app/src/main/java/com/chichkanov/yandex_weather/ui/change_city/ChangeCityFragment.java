package com.chichkanov.yandex_weather.ui.change_city;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.ui.BaseFragment;
import com.chichkanov.yandex_weather.ui.adapter.CitySuggestionAdapter;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeCityFragment extends BaseFragment implements ChangeCityView {
    @BindView(R.id.et_city_name)
    EditText etCityName;
    @BindView(R.id.rv_suggestions)
    RecyclerView rvSuggestions;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.error_relative_layout)
    RelativeLayout errorRelativeLayout;
    @InjectPresenter
    ChangeCityPresenter changeCityPresenter;
    private CitySuggestionAdapter adapter;

    public static ChangeCityFragment newInstance() {
        return new ChangeCityFragment();
    }

    @ProvidePresenter
    ChangeCityPresenter providePresenter() {
        return App.getComponent().getChangeCityPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_city, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onDrawerEnabled.setDrawerEnabled(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.settings_change_city);
        changeCityPresenter.addNavigationManager(new NavigationManager(getFragmentManager(), R.id.content_main));
        setCityNameObservable();

        rvSuggestions.setHasFixedSize(true);

        rvSuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CitySuggestionAdapter(new ArrayList<>(), prediction -> {
            changeCityPresenter.onCurrentCityChanged(prediction);
            hideKeyboard();
        });
        rvSuggestions.setAdapter(adapter);
        rvSuggestions.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    @Override
    public void showSuggestions(List<Prediction> suggestions) {
        adapter.setPredictions(suggestions);
    }

    @Override
    public void showCurrentCity(String city) {
        etCityName.setText(city);
        etCityName.setSelection(city.length());
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etCityName.getWindowToken(), 0);
    }

    @Override
    public void hideClearButton() {
        ivClear.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showClearButton() {
        ivClear.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearInput() {
        etCityName.setText("");
    }

    @Override
    public void hideSuggestionList() {
        rvSuggestions.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showSuggestionList() {
        rvSuggestions.setVisibility(View.VISIBLE);
        errorRelativeLayout.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_clear)
    void onClearClick() {
        changeCityPresenter.onClearClick();
    }

    @Override
    public void showError() {
        errorRelativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCityNameObservable() {
        changeCityPresenter.setObservable(RxTextView.textChanges(etCityName));
    }

    public interface OnCityClickListener {
        void onCityClick(Prediction prediction);
    }
}
