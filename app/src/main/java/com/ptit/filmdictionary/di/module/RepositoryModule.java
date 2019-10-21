package com.ptit.filmdictionary.di.module;

import android.app.Application;

import com.ptit.filmdictionary.data.repository.LoginRepository;
import com.ptit.filmdictionary.data.source.LoginDataSource;
import com.ptit.filmdictionary.data.source.remote.ApiSecondRequest;
import com.ptit.filmdictionary.data.source.remote.LoginRemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vanh1200 on 16/10/2019
 */

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    LoginRepository provideLoginRepository (LoginDataSource.Remote remote) {
        return new LoginRepository(remote);
    }

    @Provides
    @Singleton
    LoginDataSource.Remote provideLoginRemoteDataSource (ApiSecondRequest apiSecondRequest) {
        return new LoginRemoteDataSource(apiSecondRequest);
    }
}
