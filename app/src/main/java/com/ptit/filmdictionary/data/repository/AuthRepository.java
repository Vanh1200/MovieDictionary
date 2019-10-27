package com.ptit.filmdictionary.data.repository;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.AuthDataSource;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.LoginResponse;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class AuthRepository implements AuthDataSource.Remote {
    private static AuthRepository sInstance;
    private AuthDataSource.Remote mRemote;

//    public static AuthRepository getInstance(AuthDataSource.Remote remote) {
//        if (sInstance == null) {
//            sInstance = new AuthRepository(remote);
//        }
//        return sInstance;
//    }

    @Inject
    public AuthRepository(AuthDataSource.Remote remote) {
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
