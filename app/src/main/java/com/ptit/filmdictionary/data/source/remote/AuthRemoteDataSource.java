package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.AuthDataSource;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class AuthRemoteDataSource implements AuthDataSource.Remote {
    private ApiSecondRequest mApiRequest;

    @Inject
    public AuthRemoteDataSource(ApiSecondRequest apiRequest) {
        mApiRequest = apiRequest;
    }

    @Override
    public Single<BaseResponse<UserResponse>> login(LoginBody body) {
        return mApiRequest.login(body);
    }

    @Override
    public Single<BaseResponse<String>> register(RegisterBody body) {
        return mApiRequest.register(body);
    }
}
