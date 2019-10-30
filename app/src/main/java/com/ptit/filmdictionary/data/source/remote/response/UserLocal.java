package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class UserLocal {
    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("verifyToken")
    private String verifyToken;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }
}
