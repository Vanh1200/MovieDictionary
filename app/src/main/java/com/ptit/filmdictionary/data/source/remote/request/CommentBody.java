package com.ptit.filmdictionary.data.source.remote.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class CommentBody {
    @SerializedName("userId")
    private String useId;

    @SerializedName("content")
    private String content;

    public CommentBody() {
    }

    public CommentBody(String useId, String content) {
        this.useId = useId;
        this.content = content;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
