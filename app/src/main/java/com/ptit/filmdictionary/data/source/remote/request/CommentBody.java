package com.ptit.filmdictionary.data.source.remote.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class CommentBody {
    @SerializedName("userId")
    private String useId;

    @SerializedName("userName")
    private String userName;

    @SerializedName("content")
    private String content;

    public CommentBody() {
    }

    public CommentBody(String useId, String userName, String content) {
        this.useId = useId;
        this.userName = userName;
        this.content = content;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
