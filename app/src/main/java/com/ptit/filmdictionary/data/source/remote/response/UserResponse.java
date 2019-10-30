package com.ptit.filmdictionary.data.source.remote.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class UserResponse {
    @SerializedName("local")
    private UserLocal local;

    @SerializedName("gender")
    private String gender;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("role")
    private String role;

    @SerializedName("username")
    private String userName;

    @SerializedName("updatedAt")
    private long updatedAt;

    @SerializedName("deletedAt")
    private long deletedAt;

    @SerializedName("createdAt")
    private long createdAt;

    @SerializedName("_id")
    private String id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserLocal getLocal() {
        return local;
    }

    public void setLocal(UserLocal local) {
        this.local = local;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(long deletedAt) {
        this.deletedAt = deletedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
