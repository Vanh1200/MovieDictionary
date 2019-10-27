package com.ptit.filmdictionary.di.component;

import android.app.Application;

import com.ptit.filmdictionary.di.module.ActivityModule;
import com.ptit.filmdictionary.di.module.FragmentModule;
import com.ptit.filmdictionary.di.module.NetworkModule;
import com.ptit.filmdictionary.di.module.RepositoryModule;
import com.ptit.filmdictionary.di.module.ViewModelModule;
import com.ptit.filmdictionary.utils.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by vanh1200 on 16/10/2019
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class,
        ViewModelModule.class, ActivityModule.class, NetworkModule.class,
        RepositoryModule.class, FragmentModule.class})
public interface AppComponent extends AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MyApplication myApplication);
}
