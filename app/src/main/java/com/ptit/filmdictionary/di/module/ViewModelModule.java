package com.ptit.filmdictionary.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ptit.filmdictionary.ui.chat.ChatViewModel;
import com.ptit.filmdictionary.ui.comment.CommentViewModel;
import com.ptit.filmdictionary.ui.create_post.CreatePostViewModel;
import com.ptit.filmdictionary.ui.login.LoginViewModel;
import com.ptit.filmdictionary.ui.profile.ProfileViewModel;
import com.ptit.filmdictionary.utils.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by vanh1200 on 16/10/2019
 */

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    protected abstract ViewModel loginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel.class)
    protected abstract ViewModel commentViewModel(CommentViewModel commentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel.class)
    protected abstract ViewModel chatViewModel(ChatViewModel chatViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreatePostViewModel.class)
    protected abstract ViewModel createPostViewModel(CreatePostViewModel createPostViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    protected abstract ViewModel createProfileViewModel(ProfileViewModel profileViewModel);

}
