package com.ptit.filmdictionary.data.socket;

/**
 * Created by vanh1200 on 28/10/2019
 */
public interface SocketData {

    interface Event {
        String USER_CONNECT = "user connect";
        String USER_DISCONNECT = "user disconnect";

        //Comment
        String NEW_COMMENT = "new comment";
        String COMMENT_TYPING = "comment typing";
        String COMMENT_STOP_TYPING = "comment stop typing";

        //Chat
        String NEW_MESSAGE = "new message";
        String CHAT_TYPING = "chat typing";
        String CHAT_STOP_TYPING = "chat stop typing";

    }

    interface Key {
        String TRAILER_ID = "trailerId";
        String USER_ID = "userId";
        String SESSION_ID = "sessionId";
        String COMMENT_JSON = "comment";
    }

}
