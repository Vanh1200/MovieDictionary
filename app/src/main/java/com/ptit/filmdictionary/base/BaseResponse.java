package com.ptit.filmdictionary.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 14/10/2019
 */
public class BaseResponse <T> {
    @SerializedName("code")
    private int code;

    @SerializedName("description")
    private String description;

    @SerializedName("data")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
