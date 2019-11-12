package com.ptit.filmdictionary.data.source;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by vanh1200 on 16/10/2019
 */
public interface AuthDataSource {
    interface Local {

    }

    interface Remote {
        Single<BaseResponse<UserResponse>> login(LoginBody body);

        Single<BaseResponse<String>> register(RegisterBody body);

        Single<BaseResponse<List<UserResponse>>> searchUser(String query);
    }
}
