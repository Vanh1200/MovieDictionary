package com.ptit.filmdictionary.data.repository;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.MessageDataSource;
import com.ptit.filmdictionary.data.source.remote.request.MessageBody;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MessageRepository implements MessageDataSource.Remote {
    private MessageDataSource.Remote mRemote;

    @Inject
    public MessageRepository(MessageDataSource.Remote remote) {
        mRemote = remote;
    }

    @Override
    public Single<BaseResponse<List<MessageResponse>>> getMessageByTwoUserId(String currentUserId, String interactiveUserId, int page) {
        return mRemote.getMessageByTwoUserId(currentUserId, interactiveUserId, page);
    }

    @Override
    public Single<BaseResponse<MessageResponse>> sendMessage(String currentUserId, String interactiveUserId, MessageBody body) {
        return mRemote.sendMessage(currentUserId, interactiveUserId, body);
    }
}
