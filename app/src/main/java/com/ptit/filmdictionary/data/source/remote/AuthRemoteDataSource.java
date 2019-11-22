package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.AuthDataSource;
import com.ptit.filmdictionary.data.source.remote.request.LoginBody;
import com.ptit.filmdictionary.data.source.remote.request.RegisterBody;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Single<BaseResponse<List<UserResponse>>> searchUser(String userId, String query, String page) {
        return mApiRequest.searchUser(userId, query, page);
    }

    @Override
    public Single<BaseResponse<List<UserResponse>>> userFollower(String userId, String page) {
//        return mApiRequest.userFollower(userId, page);

        List<UserResponse> userResponses = new ArrayList<>();

        UserResponse userResponse = new UserResponse();
        userResponse.setUserName("Vanh1");
        userResponse.setId("id");
        userResponse.setAvatar("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/70655939_2547534575333614_8893890935071440896_o.jpg?_nc_cat=107&_nc_oc=AQnviRraHPW4sJ25Stf_G--mFU65vkmxCcROY-kRkF3T_fVOf3FH-6iJJ07RnZK8GW4&_nc_ht=scontent.fhan2-3.fna&oh=e130df62ab25d54259987aa2a42096fd&oe=5E470786");
        userResponses.add(userResponse);


        UserResponse userResponse2 = new UserResponse();
        userResponse2.setUserName("Vanh2");
        userResponse2.setId("id");
        userResponse2.setAvatar("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/70655939_2547534575333614_8893890935071440896_o.jpg?_nc_cat=107&_nc_oc=AQnviRraHPW4sJ25Stf_G--mFU65vkmxCcROY-kRkF3T_fVOf3FH-6iJJ07RnZK8GW4&_nc_ht=scontent.fhan2-3.fna&oh=e130df62ab25d54259987aa2a42096fd&oe=5E470786");
        userResponses.add(userResponse2);


        UserResponse userRespons3 = new UserResponse();
        userRespons3.setUserName("Vanh3");
        userRespons3.setId("id");
        userRespons3.setAvatar("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/70655939_2547534575333614_8893890935071440896_o.jpg?_nc_cat=107&_nc_oc=AQnviRraHPW4sJ25Stf_G--mFU65vkmxCcROY-kRkF3T_fVOf3FH-6iJJ07RnZK8GW4&_nc_ht=scontent.fhan2-3.fna&oh=e130df62ab25d54259987aa2a42096fd&oe=5E470786");
        userResponses.add(userRespons3);


        BaseResponse<List<UserResponse>> response = new BaseResponse<>();
        response.setCode(200);
        response.setData(userResponses);

        return Single.just(response);
    }

    @Override
    public Single<BaseResponse<List<UserResponse>>> userFollowing(String userId, String page) {
//        return mApiRequest.userFollowing(userId, page);
        List<UserResponse> userResponses = new ArrayList<>();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserName("Vanh1 following");
        userResponse.setId("id");
        userResponse.setAvatar("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/70655939_2547534575333614_8893890935071440896_o.jpg?_nc_cat=107&_nc_oc=AQnviRraHPW4sJ25Stf_G--mFU65vkmxCcROY-kRkF3T_fVOf3FH-6iJJ07RnZK8GW4&_nc_ht=scontent.fhan2-3.fna&oh=e130df62ab25d54259987aa2a42096fd&oe=5E470786");
        userResponses.add(userResponse);

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setUserName("Vanh2 following");
        userResponse2.setId("id");
        userResponse2.setAvatar("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/70655939_2547534575333614_8893890935071440896_o.jpg?_nc_cat=107&_nc_oc=AQnviRraHPW4sJ25Stf_G--mFU65vkmxCcROY-kRkF3T_fVOf3FH-6iJJ07RnZK8GW4&_nc_ht=scontent.fhan2-3.fna&oh=e130df62ab25d54259987aa2a42096fd&oe=5E470786");
        userResponses.add(userResponse2);

        UserResponse userRespons3 = new UserResponse();
        userRespons3.setUserName("Vanh3 following");
        userRespons3.setId("id");
        userRespons3.setAvatar("https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/70655939_2547534575333614_8893890935071440896_o.jpg?_nc_cat=107&_nc_oc=AQnviRraHPW4sJ25Stf_G--mFU65vkmxCcROY-kRkF3T_fVOf3FH-6iJJ07RnZK8GW4&_nc_ht=scontent.fhan2-3.fna&oh=e130df62ab25d54259987aa2a42096fd&oe=5E470786");
        userResponses.add(userRespons3);

        BaseResponse<List<UserResponse>> response = new BaseResponse<>();
        response.setCode(200);
        response.setData(userResponses);

        return Single.just(response);

    }

    @Override
    public Single<BaseResponse<UserResponse>> getUserById(String userId, String userIdGet) {
        return mApiRequest.getUser(userId, userIdGet);
    }

}
