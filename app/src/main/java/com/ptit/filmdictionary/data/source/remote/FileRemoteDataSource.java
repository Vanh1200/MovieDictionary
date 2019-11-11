package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.data.source.FileDataSource;
import com.ptit.filmdictionary.data.source.remote.response.FileResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vanh1200 on 10/11/2019
 */
public class FileRemoteDataSource implements FileDataSource.Remote {
    private ApiSecondRequest mApiSecondRequest;

    @Inject
    public FileRemoteDataSource(ApiSecondRequest apiSecondRequest) {
        mApiSecondRequest = apiSecondRequest;
    }

    @Override
    public Single<FileResponse> uploadFile(RequestBody description, MultipartBody.Part file) {
        return mApiSecondRequest.uploadFile(description, file);
    }
}
