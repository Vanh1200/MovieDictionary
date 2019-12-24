package com.ptit.filmdictionary.di.module;

import android.app.Application;
import android.content.Context;

import com.ptit.filmdictionary.data.repository.AuthRepository;
import com.ptit.filmdictionary.data.repository.CommentRepository;
import com.ptit.filmdictionary.data.repository.FeedRepository;
import com.ptit.filmdictionary.data.repository.FileRepository;
import com.ptit.filmdictionary.data.repository.MessageRepository;
import com.ptit.filmdictionary.data.repository.MovieRepository;
import com.ptit.filmdictionary.data.source.AuthDataSource;
import com.ptit.filmdictionary.data.source.CommentDataSource;
import com.ptit.filmdictionary.data.source.FeedDataSource;
import com.ptit.filmdictionary.data.source.MovieDataSource;
import com.ptit.filmdictionary.data.source.local.MovieLocalDataSource;
import com.ptit.filmdictionary.data.source.remote.ApiRequest;
import com.ptit.filmdictionary.data.source.remote.FeedRemoteDataSource;
import com.ptit.filmdictionary.data.source.FileDataSource;
import com.ptit.filmdictionary.data.source.MessageDataSource;
import com.ptit.filmdictionary.data.source.local.sharepref.PreferenceUtil;
import com.ptit.filmdictionary.data.source.remote.ApiSecondRequest;
import com.ptit.filmdictionary.data.source.remote.AuthRemoteDataSource;
import com.ptit.filmdictionary.data.source.remote.CommentRemoteDataSource;
import com.ptit.filmdictionary.data.source.remote.FileRemoteDataSource;
import com.ptit.filmdictionary.data.source.remote.MessageRemoteDataSource;
import com.ptit.filmdictionary.data.source.remote.MovieRemoteDataSource;

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
    PreferenceUtil providePreference(Application application) {
        return new PreferenceUtil(application);
    }

    @Provides
    @Singleton
    AuthRepository provideAuthRepository(AuthDataSource.Remote remote) {
        return new AuthRepository(remote);
    }

    @Provides
    @Singleton
    AuthDataSource.Remote provideAuthRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new AuthRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    CommentRepository provideCommentRepository(CommentDataSource.Remote remote) {
        return new CommentRepository(remote);
    }

    @Provides
    @Singleton
    CommentDataSource.Remote provideCommentRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new CommentRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    MessageRepository provideMessageRepository(MessageDataSource.Remote remote) {
        return new MessageRepository(remote);
    }

    @Provides
    @Singleton
    MessageDataSource.Remote provideMessageRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new MessageRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    FileRepository provideFileRepository(FileDataSource.Remote remote) {
        return new FileRepository(remote);
    }

    @Provides
    @Singleton
    FileDataSource.Remote provideFileRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new FileRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    FeedRepository provideFeedRepository(FeedDataSource.Remote remote) {
        return new FeedRepository(remote);
    }

    @Provides
    @Singleton
    FeedDataSource.Remote provideFeedRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        return new FeedRemoteDataSource(apiSecondRequest);
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDataSource.Remote remote, MovieDataSource.Local local) {
        return new MovieRepository(remote, local);
    }

    @Provides
    @Singleton
    MovieDataSource.Remote provideMovieRemoteDataSource(ApiRequest apiRequest) {
        return new MovieRemoteDataSource(apiRequest);
    }

    @Provides
    @Singleton
    MovieDataSource.Local provideMovieLocalDataSource(Application context) {
        return new MovieLocalDataSource(context);
    }

}
