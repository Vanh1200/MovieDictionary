package com.ptit.filmdictionary.data.source.remote;

import android.content.Context;

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
public class LoginRemoteDataSource implements LoginDataSource.Remote {
    private static LoginRemoteDataSource sLoginRemoteDataSource;
    private ApiSecondRequest mApiRequest;

    @Inject
    public LoginRemoteDataSource(ApiSecondRequest apiRequest) {
        mApiRequest = apiRequest;
    }

//    public static LoginRemoteDataSource getInstance(Context context) {
//        if (sLoginRemoteDataSource == null) {
//            sLoginRemoteDataSource = new LoginRemoteDataSource(NetworkService.getSecondInstance(context));
//        }
//        return sLoginRemoteDataSource;
//    }

    @Override
    public Single<BaseResponse<LoginResponse>> login(LoginBody body) {
        return mApiRequest.login(body);
    }

    @Override
    public Single<BaseResponse<String>> register(RegisterBody body) {
        return mApiRequest.register(body);
    }
}
