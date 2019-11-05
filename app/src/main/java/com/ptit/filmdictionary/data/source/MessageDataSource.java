package com.ptit.filmdictionary.data.source;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.source.remote.request.MessageBody;
import com.ptit.filmdictionary.data.source.remote.response.CommentResponse;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;

import java.util.List;

import io.reactivex.Single;

public interface MessageDataSource {
    interface Local {

    }

    interface Remote {
        Single<BaseResponse<List<MessageResponse>>> getMessageByTwoUserId(String currentUserId, String interactiveUserId, int page);

        Single<BaseResponse<MessageResponse>> sendMessage(String currentUserId, String interactiveUserId, MessageBody body);
    }
}
