package com.chichkanov.yandex_weather.ui.change_city;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.ui.BaseFragment;
import com.chichkanov.yandex_weather.ui.adapter.CitySuggestionAdapter;
import com.chichkanov.yandex_weather.ui.navigation.NavigationManager;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeCityFragment extends BaseFragment implements ChangeCityView {
    private static final int POSITION_IN_MENU = 1;

    @BindView(R.id.et_city_name)
    EditText etCityName;
    @BindView(R.id.rv_suggestions)
    RecyclerView rvSuggestions;
    @BindView(R.id.iv_clear)
    ImageView ivClear;

    private CitySuggestionAdapter adapter;


    @InjectPresenter
    ChangeCityPresenter changeCityPresenter;

    public static ChangeCityFragment newInstance() {
        return new ChangeCityFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_city, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        getActivity().setTitle(R.string.settings_change_city);
        menuItemChangeListener.onMenuItemChange(POSITION_IN_MENU);
        changeCityPresenter.addNavigationManager(new NavigationManager(getFragmentManager(), R.id.content_main));
        setCityNameObservable();

        rvSuggestions.setHasFixedSize(true);

        rvSuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CitySuggestionAdapter(new ArrayList<>(), prediction -> {
            changeCityPresenter.OnCurrentCityChanged(prediction.getDescription());
            hideKeyboard();
        });
        rvSuggestions.setAdapter(adapter);
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


    public interface OnCityClickListener {
        void onCityClick(Prediction prediction);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
    }

    @OnClick(R.id.iv_clear)
    void onClearClick() {
        changeCityPresenter.onClearClick();
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCityNameObservable() {
        changeCityPresenter.setObservable(RxTextView.textChanges(etCityName));
    }
}
