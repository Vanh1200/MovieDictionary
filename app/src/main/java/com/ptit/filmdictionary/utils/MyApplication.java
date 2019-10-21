package com.ptit.filmdictionary.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.ptit.filmdictionary.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class MyApplication extends Application implements HasActivityInjector {
    private static Context sContext;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
