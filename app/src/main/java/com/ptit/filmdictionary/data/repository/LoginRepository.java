package com.ptit.filmdictionary.data.repository;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.LoginDataSource;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.LoginResponse;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class LoginRepository implements LoginDataSource.Remote {
    private static LoginRepository sInstance;
    private LoginDataSource.Remote mRemote;

//    public static LoginRepository getInstance(LoginDataSource.Remote remote) {
//        if (sInstance == null) {
//            sInstance = new LoginRepository(remote);
//        }
//        return sInstance;
//    }

    @Inject
    public LoginRepository(LoginDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public Single<BaseResponse<LoginResponse>> login(LoginBody body) {
        return mRemote.login(body);
    }

    @Override
    public Single<BaseResponse<String>> register(RegisterBody body) {
        return mRemote.register(body);
    }
}
