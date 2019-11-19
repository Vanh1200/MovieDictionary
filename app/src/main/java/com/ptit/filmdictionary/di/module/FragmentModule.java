package com.ptit.filmdictionary.di.module;

import com.ptit.filmdictionary.ui.comment.CommentDialogFragment;
import com.ptit.filmdictionary.ui.feed.FeedFragment;
import com.ptit.filmdictionary.ui.follower.FollowFragment;
import com.ptit.filmdictionary.ui.login.LoginFragment;
import com.ptit.filmdictionary.ui.login.RegisterFragment;
import com.ptit.filmdictionary.ui.movie_detail.info.MovieInfoFragment;
import com.ptit.filmdictionary.ui.profile.ProfileFragment;
import com.ptit.filmdictionary.ui.profile.ProfileMovieFragment;
import com.ptit.filmdictionary.ui.profile.ProfilePostFragment;
import com.ptit.filmdictionary.ui.search_user.SearchUserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vanh1200 on 16/10/2019
 */
@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment contributeRegisterFragment();

    @ContributesAndroidInjector
    abstract MovieInfoFragment contributeMovieInfoFragment();

    @ContributesAndroidInjector
    abstract CommentDialogFragment contributeCommentDialogFragment();

    @ContributesAndroidInjector
    abstract FeedFragment contributeFeedFragment();

    @ContributesAndroidInjector
    abstract ProfileMovieFragment contributeProfileMovieFragment();

    @ContributesAndroidInjector
    abstract ProfilePostFragment contributeProfilePostFragment();

    @ContributesAndroidInjector
    abstract FollowFragment contributeFollowFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

}
