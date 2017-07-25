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
    public final String IS_LOGIN = "IsLoggedIn";
    public final String KEY_NATIONAL_ID = "nationalID";
    public final String KEY_ID = "ID";
    public final String KEY_ID_REF = "IDREF";
    public final String KEY_FULL_NAME = "FULLNAME";
    public final String KEY_USER_TYPE = "USERTYPE";
    public final String KEY_USER_TYPE_STRING = "USERTYPE_STRING";
    public final String KEY_USER_IMAGE = "UserImage";
    public final String KEY_USER_SCHOOL = "UserSchool";
    public final String KEY_USER_SCHOOL_ID = "UserSchoolID";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        settings_pref = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void createLoginSession(String nationalID, String userId, String idRef, String fullName, String userTypeString, int userType, String userImage, String userSchool, String schoolID) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID_REF, idRef);
        editor.putString(KEY_ID, userId);
        editor.putString(KEY_NATIONAL_ID, nationalID);
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_USER_TYPE_STRING, userTypeString);
        editor.putInt(KEY_USER_TYPE, userType);
        editor.putString(KEY_USER_IMAGE, userImage);
        editor.putString(KEY_USER_SCHOOL, userSchool);
        editor.putString(KEY_USER_SCHOOL_ID, schoolID);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID_REF, pref.getString(KEY_ID_REF, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NATIONAL_ID, pref.getString(KEY_NATIONAL_ID, null));
        user.put(KEY_FULL_NAME, pref.getString(KEY_FULL_NAME, null));
        user.put(KEY_USER_TYPE_STRING, pref.getString(KEY_USER_TYPE_STRING, null));
        user.put(KEY_USER_TYPE, String.valueOf(pref.getInt(KEY_USER_TYPE, 1)));
        user.put(KEY_USER_IMAGE, pref.getString(KEY_USER_IMAGE, null));
        user.put(KEY_USER_SCHOOL, pref.getString(KEY_USER_SCHOOL, null));
        user.put(KEY_USER_SCHOOL_ID, pref.getString(KEY_USER_SCHOOL_ID, null));
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
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
