package com.linked_sys.hasatraining.core;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.linked_sys.hasatraining.activities.SignInActivity;

import java.util.HashMap;


public class SessionManager {
    public SharedPreferences pref;
    public SharedPreferences settings_pref;
    public SharedPreferences.Editor editor;
    public Context mContext;
    public int PRIVATE_MODE = 0;
    public final String PREF_NAME = AppController.TAG;
    public final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public final String IS_LOGIN = "IsLoggedIn";
    public final String KEY_EMAIL = "email";
    public final String KEY_TOKEN = "TOKEN";
    public final String KEY_PASSWORD = "password";
    public final String KEY_REF = "REF";
    public final String KEY_FULL_NAME = "FULLNAME";
    public final String KEY_USER_TYPE = "USERTYPE";
    public final String KEY_USER_ID = "VIEW_ID";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        settings_pref = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setUserViewID(int userViewID) {
        editor.putInt(KEY_USER_ID, userViewID);
        editor.commit();
    }

    public int getUserViewID() {
        return pref.getInt(KEY_USER_ID, 1);
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void createLoginSession(String email, String password, String token, String ref, String fullName, String userType) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_REF, ref);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_USER_TYPE, userType);
        editor.putInt(KEY_USER_ID, 1);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_REF, pref.getString(KEY_REF, null));
        user.put(KEY_FULL_NAME, pref.getString(KEY_FULL_NAME, null));
        return user;
    }

    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            Intent i = new Intent(mContext, SignInActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(mContext, SignInActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
        setFirstTimeLaunch(false);
        CacheHelper.getInstance().ACode = "";
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getUserToken() {
        return pref.getString(KEY_TOKEN, null);
    }
}
