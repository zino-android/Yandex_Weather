package com.chichkanov.yandex_weather;

import android.app.Application;

import com.chichkanov.yandex_weather.background.AutoUpdateJob;
import com.chichkanov.yandex_weather.background.AutoUpdateJobCreator;
import com.chichkanov.yandex_weather.di.AppComponent;
import com.chichkanov.yandex_weather.di.DaggerAppComponent;
import com.chichkanov.yandex_weather.di.modules.InteractorModule;
import com.chichkanov.yandex_weather.di.modules.NetworkModule;
import com.chichkanov.yandex_weather.di.modules.RepositoryModule;
import com.chichkanov.yandex_weather.utils.Settings;
import com.evernote.android.job.JobManager;
import com.facebook.stetho.Stetho;

public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initAutoUpdate();
        initStetho();
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
        long time = new Settings(getApplicationContext()).getAutoRefreshTime();
        if (time != 0) AutoUpdateJob.scheduleJob(time);
    }

    private void initDagger() {
        component = DaggerAppComponent
                .builder()
                .networkModule(new NetworkModule())
                .repositoryModule(new RepositoryModule())
                .interactorModule(new InteractorModule())
                .build();
        component.inject(this);
    }
}
