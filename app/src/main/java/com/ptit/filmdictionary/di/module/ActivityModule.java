package com.ptit.filmdictionary.di.module;

import com.ptit.filmdictionary.ui.chat.ChatActivity;
import com.ptit.filmdictionary.ui.create_post.CreatePostActivity;
import com.ptit.filmdictionary.ui.feed.FeedFragment;
import com.ptit.filmdictionary.ui.login.LoginActivity;
import com.ptit.filmdictionary.ui.main.MainActivity;
import com.ptit.filmdictionary.ui.movie_detail.MovieDetailActivity;
import com.ptit.filmdictionary.ui.profile.ProfileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vanh1200 on 16/10/2019
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract LoginActivity contributeLoginActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MovieDetailActivity contributeMovieDetailActivity();

    @ContributesAndroidInjector(modules = FeedFragment.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract ChatActivity contributeChatActivity();

    @ContributesAndroidInjector
    abstract ProfileActivity contributeProfileActivity();

    @ContributesAndroidInjector
    abstract CreatePostActivity contributeCreatePostActivity();
}
