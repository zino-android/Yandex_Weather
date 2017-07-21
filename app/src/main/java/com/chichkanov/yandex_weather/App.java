package com.chichkanov.yandex_weather;

import android.app.Application;

import com.chichkanov.yandex_weather.background.AutoUpdateJob;
import com.chichkanov.yandex_weather.background.AutoUpdateJobCreator;
import com.chichkanov.yandex_weather.di.AppComponent;
import com.chichkanov.yandex_weather.di.DaggerAppComponent;
import com.chichkanov.yandex_weather.di.modules.ApplicationModule;
import com.chichkanov.yandex_weather.utils.Settings;
import com.evernote.android.job.JobManager;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

public class App extends Application {

    private static AppComponent component;

    @Inject
    Settings settings;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initAutoUpdate();
        if (BuildConfig.DEBUG) {
            initStetho();
        }
    }

    public static AppComponent getComponent() {
        return component;
    }

    private void initStetho() {
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(getApplicationContext())
        );
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

    private void initAutoUpdate() {
        JobManager.create(this).addJobCreator(new AutoUpdateJobCreator());
        long time = settings.getAutoRefreshTime();
        if (time != 0) AutoUpdateJob.scheduleJob(time);
    }

    private void initDagger() {
        component = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .build();
        component.inject(this);
    }
}
