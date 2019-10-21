package com.ptit.filmdictionary.di.module;

import android.app.Application;

import com.ptit.filmdictionary.data.source.remote.ApiRequest;
import com.ptit.filmdictionary.data.source.remote.ApiSecondRequest;
import com.ptit.filmdictionary.data.source.remote.NetworkService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vanh1200 on 16/10/2019
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    ApiSecondRequest provideApiSecondRequest (Application application) {
        return NetworkService.getSecondInstance(application.getApplicationContext());
    }
}
