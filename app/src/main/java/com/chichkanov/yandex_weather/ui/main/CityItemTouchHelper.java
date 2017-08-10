package com.chichkanov.yandex_weather.ui.main;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.chichkanov.yandex_weather.ui.adapter.CityAdapter;



public class CityItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private OnCityDeleteListener listener;
    private CityAdapter adapter;

    public CityItemTouchHelper(OnCityDeleteListener listener, CityAdapter adapter) {
        super(0, ItemTouchHelper.RIGHT);
        this.listener = listener;
        this.adapter = adapter;
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof CityAdapter.AddViewHolder) return 0;
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (!adapter.getCities().get(viewHolder.getAdapterPosition()).isSelected()) {
            listener.onDelete(viewHolder.getAdapterPosition());
        } else {
            adapter.notifyItemChanged(viewHolder.getAdapterPosition());
        }
    }

    public interface OnCityDeleteListener {
        void onDelete(int position);
    }
}
