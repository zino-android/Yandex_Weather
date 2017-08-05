package com.chichkanov.yandex_weather.background;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class AutoUpdateJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case AutoUpdateJob.TAG:
                return new AutoUpdateJob();
            default:
                return null;
        }
    }
}
