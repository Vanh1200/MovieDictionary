package com.ptit.filmdictionary.data.source.remote;

import com.ptit.filmdictionary.base.BaseResponse;
import com.ptit.filmdictionary.data.model.MessageType;
import com.ptit.filmdictionary.data.source.MessageDataSource;
import com.ptit.filmdictionary.data.source.remote.request.MessageBody;
import com.ptit.filmdictionary.data.source.remote.request.MessageRequest;
import com.ptit.filmdictionary.data.source.remote.response.MessageResponse;
import com.ptit.filmdictionary.data.source.remote.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MessageRemoteDataSource implements MessageDataSource.Remote {
    private ApiSecondRequest mApiSecondRequest;

    @Inject
    public MessageRemoteDataSource (ApiSecondRequest apiSecondRequest) {
        mApiSecondRequest = apiSecondRequest;
    }

    @Override
    public Single<BaseResponse<List<MessageResponse>>> getMessageByTwoUserId(String currentUserId, String interactiveUserId, int page) {
        return mApiSecondRequest.getMessageByTwoUserId(new MessageRequest(currentUserId, interactiveUserId, ""), page);
//        List<MessageResponse> messageResponses = new ArrayList<>();
//
//        UserResponse sender = new UserResponse();
//        sender.setAvatar("https://instagram.fhan2-3.fna.fbcdn.net/vp/c5d61550d5e23306e49a738418a02f17/5E477790/t51.2885-15/sh0.08/e35/s640x640/71349029_167196114397628_7035417197843271507_n.jpg?_nc_ht=instagram.fhan2-3.fna.fbcdn.net&_nc_cat=108");
//        sender.setUserName("axitpicric");
//        sender.setId("5d9cc4ae3405950023ab0bd0");
//
//        UserResponse receiver = new UserResponse();
//        receiver.setId("5d9cc76c3405950023ab0bd1");
//        receiver.setUserName("Anhvodanh99");
//        receiver.setAvatar("https://scontent.fhan5-5.fna.fbcdn.net/v/t1.0-1/c0.0.960.960a/p960x960/67401869_1654800764663986_4174444255906889728_o.jpg?_nc_cat=108&_nc_oc=AQlKBdW1UlACxdLxwJEJRm5iVhe_iuGb8t_Uk-iV6rTO3nmiOfK98bDeO0kpdwaEJTA&_nc_ht=scontent.fhan5-5.fna&oh=b40104e67721d38a466bf6df8e6f4179&oe=5E4AAECF");
//
//        MessageResponse mess1 = new MessageResponse();
//        mess1.setSernder(sender);
//        mess1.setReceiver(receiver);
//        mess1.setType(MessageType.TEXT_MESSAGE);
//        mess1.setText("1. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess1);
//
//        MessageResponse mess2 = new MessageResponse();
//        mess2.setSernder(receiver);
//        mess2.setReceiver(sender);
//        mess2.setType(MessageType.TEXT_MESSAGE);
//        mess2.setText("2. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess2);
//
//        MessageResponse mess3 = new MessageResponse();
//        mess3.setSernder(sender);
//        mess3.setReceiver(receiver);
//        mess3.setType(MessageType.TEXT_MESSAGE);
//        mess3.setText("3. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess3);
//
//        MessageResponse mess4 = new MessageResponse();
//        mess4.setSernder(sender);
//        mess4.setReceiver(receiver);
//        mess4.setType(MessageType.TEXT_MESSAGE);
//        mess4.setText("1. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess4);
//
//        MessageResponse mess5 = new MessageResponse();
//        mess5.setSernder(receiver);
//        mess5.setReceiver(sender);
//        mess5.setType(MessageType.TEXT_MESSAGE);
//        mess5.setText("2. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess5);
//
//        MessageResponse mess6 = new MessageResponse();
//        mess6.setSernder(sender);
//        mess6.setReceiver(receiver);
//        mess6.setType(MessageType.TEXT_MESSAGE);
//        mess6.setText("3. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess6);
//
//        MessageResponse mess7 = new MessageResponse();
//        mess7.setSernder(sender);
//        mess7.setReceiver(receiver);
//        mess7.setType(MessageType.TEXT_MESSAGE);
//        mess7.setText("1. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess7);
//
//        MessageResponse mess8 = new MessageResponse();
//        mess8.setSernder(receiver);
//        mess8.setReceiver(sender);
//        mess8.setType(MessageType.TEXT_MESSAGE);
//        mess8.setText("2. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess8);
//
//        MessageResponse mess9 = new MessageResponse();
//        mess9.setSernder(sender);
//        mess9.setReceiver(receiver);
//        mess9.setType(MessageType.TEXT_MESSAGE);
//        mess9.setText("3. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess9);
//
//        MessageResponse mess10 = new MessageResponse();
//        mess10.setSernder(sender);
//        mess10.setReceiver(receiver);
//        mess10.setType(MessageType.TEXT_MESSAGE);
//        mess10.setText("1. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess10);
//
//        MessageResponse mess11 = new MessageResponse();
//        mess11.setSernder(receiver);
//        mess11.setReceiver(sender);
//        mess11.setType(MessageType.TEXT_MESSAGE);
//        mess11.setText("2. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess11);
//
//        MessageResponse mess12 = new MessageResponse();
//        mess12.setSernder(sender);
//        mess12.setReceiver(receiver);
//        mess12.setType(MessageType.TEXT_MESSAGE);
//        mess12.setText("3. Đây là tiếng nói Việt Nam phát đi từ Hà Nội thủ đô nước cộng hòa xã hội chủ nghĩa Việt Nam");
//        messageResponses.add(mess12);
//
//        BaseResponse<List<MessageResponse>> response = new BaseResponse<>();
//        response.setCode(200);
//        response.setDescription("Deo co description dau");
//        response.setData(messageResponses);
//
//        return Single.just(response);
    }

    @Override
    public Single<BaseResponse<MessageResponse>> sendMessage(String currentUserId, String interactiveUserId, String text) {
        return mApiSecondRequest.sendMessage(new MessageRequest(currentUserId, interactiveUserId, text));
    }
}
