package com.ptit.filmdictionary.data.source.remote;

import android.content.Context;

public class NetworkService {
    private static ApiRequest sApiRequest;
    private static ApiSecondRequest sApiSecondRequest;

    public static ApiRequest getInstance(Context context) {
        if (sApiRequest == null) {
            sApiRequest = RetrofitBuilder.getInstance(context).create(ApiRequest.class);
        }
        return sApiRequest;
    }

    public static ApiSecondRequest getSecondInstance(Context context) {
        if (sApiSecondRequest == null) {
            sApiSecondRequest = RetrofitBuilder.getSecondInstance(context).create(ApiSecondRequest.class);
        }
        return sApiSecondRequest;
    }
}
