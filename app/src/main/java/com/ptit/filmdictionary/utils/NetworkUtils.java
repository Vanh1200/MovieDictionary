package com.ptit.filmdictionary.utils;

import com.google.gson.Gson;
import com.ptit.filmdictionary.base.BaseErrorResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by vanh1200 on 22/10/2019
 */
public class NetworkUtils {

    public static BaseErrorResponse getErrorResponse (Throwable throwable) {
        if (throwable instanceof HttpException && ((HttpException) throwable).code() == 401) {
            ResponseBody body = ((HttpException) throwable).response().errorBody();
            try {
                return new Gson().fromJson(body.string(), BaseErrorResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
