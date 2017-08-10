package com.chichkanov.yandex_weather.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.places.Prediction;
import com.chichkanov.yandex_weather.ui.change_city.ChangeCityFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CitySuggestionAdapter extends RecyclerView.Adapter<CitySuggestionAdapter.ViewHolder> {
    private List<Prediction> predictions;
    private ChangeCityFragment.OnCityClickListener listener;

    public CitySuggestionAdapter(List<Prediction> predictions, ChangeCityFragment.OnCityClickListener listener) {
        this.predictions = predictions;
        this.listener = listener;
    }

    @Override
    public CitySuggestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.change_city_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cityTextView.setText(predictions.get(position).getStructuredFormatting().getMainText());
        holder.countryTextView.setText(predictions.get(position).getStructuredFormatting().getSecondaryText());
        holder.itemView.setOnClickListener(view -> listener.onCityClick(predictions.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return predictions.size();
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_name)
        TextView cityTextView;
        @BindView(R.id.tv_country_name)
        TextView countryTextView;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
