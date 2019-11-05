package com.ptit.filmdictionary.data.source.remote.request;

import com.google.gson.annotations.SerializedName;

public class MessageRequest {
    @SerializedName("sender")
    private String sender;

    @SerializedName("receiver")
    private String receiver;

    public MessageRequest() {
    }

    public MessageRequest(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
