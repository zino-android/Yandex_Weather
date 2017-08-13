package com.chichkanov.yandex_weather.ui.adapter;

import android.support.v7.util.DiffUtil;

import com.chichkanov.yandex_weather.model.Forecast;

import java.util.List;

/**
 * Created by Алексей on 06.08.2017.
 */

public class ForecastItemDiffCallback extends DiffUtil.Callback {
    protected List<Forecast> oldList, newList;

    public ForecastItemDiffCallback(List<Forecast> oldList, List<Forecast> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldList.get(oldItemPosition).getCityId() == newList.get(newItemPosition).getCityId()
                && oldList.get(oldItemPosition).getDateTime() == newList.get(newItemPosition).getDateTime());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
