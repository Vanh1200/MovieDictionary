package com.ptit.filmdictionary.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ptit.filmdictionary.ui.login.LoginViewModel;
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

}
