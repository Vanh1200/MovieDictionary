package com.ptit.filmdictionary.data.source.local.sharepref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vanh1200 on 15/10/2019
 */
public class PreferenceUtil {
    private static PreferenceUtil sInstance;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String PREFERENCE_NAME = "preference_name";

    public static PreferenceUtil getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceUtil(context);
        }
        return sInstance;
    }

    private PreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }
}
