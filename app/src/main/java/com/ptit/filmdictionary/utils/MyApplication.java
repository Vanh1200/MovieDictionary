package com.ptit.filmdictionary.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.ptit.filmdictionary.base.BaseFeed;
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
    private MutableLiveData<BaseFeed> mLiveCreatePost = new MutableLiveData<>();

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
            mSocket = IO.socket(Constants.BASE_VANH_URL_DEV);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public MutableLiveData<BaseFeed> getLiveCreatePost() {
        return mLiveCreatePost;
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
