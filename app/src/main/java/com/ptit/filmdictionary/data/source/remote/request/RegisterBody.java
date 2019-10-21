package com.ptit.filmdictionary.data.source.remote.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class RegisterBody {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private String gender;
}
