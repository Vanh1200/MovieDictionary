package com.ptit.filmdictionary.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vanh1200 on 16/10/2019
 */
public class UserResponse implements Parcelable {
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

    @SerializedName("isFollow")
    private boolean isFollow;

    @SerializedName("updatedAt")
    private long updatedAt;

    @SerializedName("deletedAt")
    private long deletedAt;

    @SerializedName("createdAt")
    private long createdAt;

    @SerializedName("_id")
    private String id;

    public UserResponse() {
    }

    protected UserResponse(Parcel in) {
        gender = in.readString();
        phone = in.readString();
        address = in.readString();
        avatar = in.readString();
        role = in.readString();
        userName = in.readString();
        updatedAt = in.readLong();
        deletedAt = in.readLong();
        createdAt = in.readLong();
        id = in.readString();
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(gender);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(avatar);
        parcel.writeString(role);
        parcel.writeString(userName);
        parcel.writeLong(updatedAt);
        parcel.writeLong(deletedAt);
        parcel.writeLong(createdAt);
        parcel.writeString(id);
    }
}
