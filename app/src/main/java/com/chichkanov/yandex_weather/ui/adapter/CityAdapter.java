package com.chichkanov.yandex_weather.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chichkanov.yandex_weather.R;
import com.chichkanov.yandex_weather.model.CityMenu;
import com.chichkanov.yandex_weather.ui.main.CityMenuDiffUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CityMenu> cities;
    private OnCitySelectListener listener;
    private OnAddCityListener addCityListener;
    private Context context;

    private final int ITEM_VIEW_TYPE = 1;
    private final int ADD_ITEM_VIEW_TYPE = 2;

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    public static class AddViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.add_city_button)
        Button addButton;

        AddViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }

    public CityAdapter(Context context, List<CityMenu> cities, OnCitySelectListener listener,
                       OnAddCityListener addCityListener) {
        this.context = context;
        this.cities = cities;
        this.listener = listener;
        this.addCityListener = addCityListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.city_item, parent, false);


            ViewHolder vh = new CityAdapter.ViewHolder(v);

            v.setOnClickListener(onClickListener -> {
                listener.onCitySelected(cities.get(vh.getAdapterPosition()));
            });
            return vh;
        }
        if (viewType == ADD_ITEM_VIEW_TYPE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_city_item, parent, false);


            AddViewHolder vh = new CityAdapter.AddViewHolder(v);

            vh.addButton.setOnClickListener(onClickListener -> {
                addCityListener.onAddCityClicked();
            });
            return vh;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (getItemViewType(position) == ITEM_VIEW_TYPE) {
            ViewHolder holder = (CityAdapter.ViewHolder) viewHolder;
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
        if (getItemViewType(position) == ADD_ITEM_VIEW_TYPE) {
            AddViewHolder holder = (CityAdapter.AddViewHolder) viewHolder;

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == cities.size()) {
            return ADD_ITEM_VIEW_TYPE;
        }
        return ITEM_VIEW_TYPE;

    }

    @Override
    public int getItemCount() {
        return cities.size() + 1;
    }

    public void setCities(List<CityMenu> cities) {
        CityMenuDiffUtils diffCallback = new CityMenuDiffUtils(this.cities, cities);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.cities.clear();
        this.cities.addAll(cities);
        diffResult.dispatchUpdatesTo(this);
    }

    public List<CityMenu> getCities() {
        return cities;
    }

    public interface OnCitySelectListener {
        void onCitySelected(CityMenu city);
    }

    public interface OnAddCityListener {
        void onAddCityClicked();
    }
}
