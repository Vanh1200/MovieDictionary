package com.ptit.filmdictionary.data.source;

import com.ptit.filmdictionary.data.source.remote.response.FileResponse;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vanh1200 on 10/11/2019
 */
public interface FileDataSource {
    interface Remote {
        Single<FileResponse> uploadFile(RequestBody description, MultipartBody.Part file);
    }
}
