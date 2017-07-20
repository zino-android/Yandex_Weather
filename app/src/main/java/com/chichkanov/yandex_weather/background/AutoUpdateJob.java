package com.chichkanov.yandex_weather.background;

import android.support.annotation.NonNull;

import com.chichkanov.yandex_weather.App;
import com.chichkanov.yandex_weather.repository.WeatherRepositoryImpl;
import com.chichkanov.yandex_weather.utils.Constants;
import com.chichkanov.yandex_weather.utils.IOtools;
import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class AutoUpdateJob extends Job {

    @Inject
    WeatherRepositoryImpl repository;

    static final String TAG = "auto_update_job";

    AutoUpdateJob() {
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        repository.getWeather(Constants.CITY)
                .doOnError(Throwable::printStackTrace)
                .subscribe(IOtools::saveCurrentWeather);
        return Result.SUCCESS;
    }

    public static void scheduleJob(long autoUpdateTime) {

        new JobRequest.Builder(AutoUpdateJob.TAG)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setPeriodic(TimeUnit.MINUTES.toMillis(autoUpdateTime), TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }
}
