package com.ptit.filmdictionary.data.repository;

import com.ptit.filmdictionary.data.source.FileDataSource;
import com.ptit.filmdictionary.data.source.remote.response.FileResponse;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vanh1200 on 10/11/2019
 */
public class FileRepository implements FileDataSource.Remote {
    private FileDataSource.Remote mRemote;

    @Inject
    public FileRepository(FileDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public Single<FileResponse> uploadFile(RequestBody description, MultipartBody.Part file) {
        return mRemote.uploadFile(description, file);
    }
}
