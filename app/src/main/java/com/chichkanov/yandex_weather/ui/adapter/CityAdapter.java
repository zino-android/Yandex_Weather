package com.chichkanov.yandex_weather.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CityMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<CityMenu> cities;
    private OnCitySelectListener listener;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city_name)
        TextView tvCityName;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_temp)
        TextView tvTemp;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    public CityAdapter(Context context, List<CityMenu> cities, OnCitySelectListener listener) {
        this.context = context;
        this.cities = cities;
        this.listener = listener;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);


        CityAdapter.ViewHolder vh = new CityAdapter.ViewHolder(v);

        v.setOnClickListener(onClickListener -> {
            listener.onCitySelected(cities.get(vh.getAdapterPosition()));
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        holder.tvCityName.setText(cities.get(position).getName());
        holder.tvTemp.setText(context.getString(R.string.weather_temperature, Math.round(cities.get(position).getTemp())));
        if (cities.get(position).isSelected()) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.marker_circle);
            drawable = DrawableCompat.wrap(drawable).mutate();
            DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.colorAccent));
            holder.ivIcon.setImageDrawable(drawable);
        } else {
            holder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.marker_circle));
        }
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void setCities(List<CityMenu> predictions) {
        this.cities = predictions;
        notifyDataSetChanged();
    }

    public interface OnCitySelectListener {
        void onCitySelected(CityMenu city);
    }
}
