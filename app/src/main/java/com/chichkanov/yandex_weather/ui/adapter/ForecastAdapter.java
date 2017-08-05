package com.chichkanov.yandex_weather.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private List<Forecast> forecasts;
    private AdapterView.OnItemClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_name)
        TextView textView;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ForecastAdapter(List<Forecast> forecasts, AdapterView.OnItemClickListener listener) {
        this.forecasts = forecasts;
        this.listener = listener;
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.change_city_item, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Date now = new Date(forecasts.get(position).getDateTime() * 1000);
// or you can use
// long millis = System.currentTimeInMillis();
// Date now = new Date(millis);

        String datePattern = "dd-MM-yyyy HH-MM-SS";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        String formattedDate = formatter.format(now);

        holder.textView.setText(formattedDate + " " + String.valueOf(forecasts.get(position).getDayTemp()
                + " / " + String.valueOf(forecasts.get(position).getNightTemp())));
//        holder.itemView.setOnClickListener(view -> listener.onCityClick(forecasts.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
        notifyItemRangeChanged(0, forecasts.size());
    }
}
