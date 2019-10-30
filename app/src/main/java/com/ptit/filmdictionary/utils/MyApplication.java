package com.ptit.filmdictionary.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.ptit.filmdictionary.di.component.DaggerAppComponent;

import java.net.URISyntaxException;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.socket.client.IO;
import io.socket.client.Socket;

public class MyApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {
    private static Context sContext;
    private Socket mSocket;

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjectorFragment;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        try {
            mSocket = IO.socket(Constants.BASE_VANH_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public Socket getSocket() {
        return mSocket;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjectorFragment;
    }
}
