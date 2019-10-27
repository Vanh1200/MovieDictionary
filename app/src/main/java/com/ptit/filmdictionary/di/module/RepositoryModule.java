package com.ptit.filmdictionary.di.module;

import android.app.Application;

import com.ptit.filmdictionary.data.repository.AuthRepository;
import com.ptit.filmdictionary.data.repository.CommentRepository;
import com.ptit.filmdictionary.data.source.AuthDataSource;
import com.ptit.filmdictionary.data.source.CommentDataSource;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.ApiSecondRequest;
import com.ptit.filmdictionary.data.source.remote.AuthRemoteDataSource;
import com.ptit.filmdictionary.data.source.remote.CommentRemoteDataSource;

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
    AuthRepository provideAuthRepository(AuthDataSource.Remote remote) {
        return new AuthRepository(remote);
    }

    @Provides
    @Singleton
    CommentRepository provideCommentRepository(CommentDataSource.Remote remote) {
        return new CommentRepository(remote);
    }

    @Provides
    @Singleton
    AuthDataSource.Remote provideAuthRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new AuthRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    CommentDataSource.Remote provideCommentRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new CommentRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    PreferenceUtil providePreference(Application application) {
        return new PreferenceUtil(application);
    }
}
