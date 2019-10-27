package com.ptit.filmdictionary.data.source.local.sharepref;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import javax.inject.Inject;

/**
 * Created by vanh1200 on 15/10/2019
 */
public class PreferenceUtil {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static PreferenceUtil sInstance;
    private static final String PREFERENCE_NAME = "preference_name";

    public static PreferenceUtil getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new PreferenceUtil(context);
        }
        return sInstance;
    }

    @Inject
    public PreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void setEmail (String email) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(PreferenceData.EMAIL, email);
        mEditor.apply();
    }

    public String getEmail () {
        return mSharedPreferences.getString(PreferenceData.EMAIL, "");
    }

    public void setUserName (String userName) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(PreferenceData.USER_NAME, userName);
        mEditor.apply();
    }

    public String getUserName () {
        return mSharedPreferences.getString(PreferenceData.USER_NAME, "");
    }

    public void setPassword (String password) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(PreferenceData.PASSWORD, password);
        mEditor.apply();
    }

    public String getPassword () {
        return mSharedPreferences.getString(PreferenceData.PASSWORD, "");
    }

    public void setUserId (String id) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(PreferenceData.ID, id);
        mEditor.apply();
    }

    public String getUserId () {
        return mSharedPreferences.getString(PreferenceData.ID, "");
    }

    public void setUserAvatar (String avatar) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(PreferenceData.AVATAR, avatar);
        mEditor.apply();
    }

    public String getUserAvatar () {
        return mSharedPreferences.getString(PreferenceData.AVATAR, "");
    }
}
