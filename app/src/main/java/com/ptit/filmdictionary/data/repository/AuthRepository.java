package com.ptit.filmdictionary.data.repository;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.AuthDataSource;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class AuthRepository implements AuthDataSource.Remote {
    private AuthDataSource.Remote mRemote;

    @Inject
    public AuthRepository(AuthDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public Single<BaseResponse<UserResponse>> login(LoginBody body) {
        return mRemote.login(body);
    }

    @Override
    public Single<BaseResponse<String>> register(RegisterBody body) {
        return mRemote.register(body);
    }

    @Override
    public Single<BaseResponse<List<UserResponse>>> searchUser(String userId, String query, String page) {
        return mRemote.searchUser(userId, query, page);
    }

    @Override
    public Single<BaseResponse<List<UserResponse>>> userFollower(String userId, String page) {
        return mRemote.userFollower(userId, page);
    }

    @Override
    public Single<BaseResponse<List<UserResponse>>> userFollowing(String userId, String page) {
        return mRemote.userFollowing(userId, page);
    }

    @Override
    public Single<BaseResponse<UserResponse>> getUserById(String userId, String userIdGet) {
        return null;
    }
}
