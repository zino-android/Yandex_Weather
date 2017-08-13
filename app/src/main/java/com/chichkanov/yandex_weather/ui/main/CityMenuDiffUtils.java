package com.chichkanov.yandex_weather.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.chichkanov.yandex_weather.model.CityMenu;

import java.util.List;



public class CityMenuDiffUtils  extends DiffUtil.Callback {

    private final List<CityMenu> oldList;
    private final List<CityMenu> newList;

    public CityMenuDiffUtils(@NonNull List<CityMenu> oldList,
                                 @NonNull List<CityMenu> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition)
                .getCityId() == (newList.get(newItemPosition).getCityId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
