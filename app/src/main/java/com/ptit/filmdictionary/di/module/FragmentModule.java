package com.ptit.filmdictionary.di.module;

import com.ptit.filmdictionary.ui.login.LoginFragment;
import com.ptit.filmdictionary.ui.login.RegisterFragment;

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
}
