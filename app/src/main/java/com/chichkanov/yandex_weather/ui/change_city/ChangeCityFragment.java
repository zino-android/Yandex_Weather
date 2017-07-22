package com.chichkanov.yandex_weather.ui.change_city;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.ui.adapter.CitySuggestionAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangeCityFragment extends MvpAppCompatFragment implements ChangeCityView {

    @BindView(R.id.et_city_name)
    EditText etCityName;
    @BindView(R.id.rv_suggestions)
    RecyclerView rvSuggestions;

    private Unbinder unbinder;
    private CitySuggestionAdapter adapter;

    @InjectPresenter
    ChangeCityPresenter changeCityPresenter;

    public static ChangeCityFragment newInstance() {
        ChangeCityFragment fragment = new ChangeCityFragment();

        return fragment;
    }

    public ChangeCityFragment() {
        // Required empty public constructor
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
        getActivity().setTitle(R.string.menu_change_city);

        RxTextView.textChangeEvents(etCityName)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> {
                    String text = e.text().toString().trim();
                    changeCityPresenter.loadCitySuggestion(text);
                });

        rvSuggestions.setHasFixedSize(true);

        rvSuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CitySuggestionAdapter(new ArrayList<>(), new OnCityClickListener() {
            @Override
            public void onCityClick(Prediction prediction) {
                changeCityPresenter.OnCurrentCityChanged(prediction.getDescription());
            }
        });
        rvSuggestions.setAdapter(adapter);
    }

    @Override
    public void showSuggestions(List<Prediction> suggestions) {
        adapter.setPredictions(suggestions);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnCityClickListener {
        void onCityClick(Prediction prediction);
    }


}
