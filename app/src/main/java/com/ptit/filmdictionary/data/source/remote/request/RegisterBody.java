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

    public RegisterBody(String email, String password, String gender) {
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
