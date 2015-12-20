package com.keshe.contacts_keshe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreference {
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String IS_LOGGEDIN = "IS_LOGGEDIN";
    public static final String MOBILE = "MOBILE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String TOKEN = "TOKEN";

    public SharedPreference() {
        super();
    }

    public int isLoggedIn(Context context) {
        int value = getIntValue(context, IS_LOGGEDIN);
        return value;
    }

    public void logIn(Context context) {
        saveInt(context, IS_LOGGEDIN, 1);
    }

    public void logOut(Context context) {
        saveInt(context, IS_LOGGEDIN, 0);
    }

    public String getMobile(Context context) {
        String mobile = getStringValue(context, MOBILE);
        return mobile;
    }

    public void setMobile(Context context, String mobile) {
        saveString(context, MOBILE, mobile);
    }

    public String getUserName(Context context) {
        String username = getStringValue(context, USERNAME);
        return username;
    }

    public void setUserName(Context context, String username) {
        saveString(context, USERNAME, username);
    }

    public String getPassword(Context context) {
        String password = getStringValue(context, PASSWORD);
        return password;
    }

    public void setPassword(Context context, String password) {
        saveString(context, PASSWORD, password);
    }

    public String getToken(Context context) {
        String token = getStringValue(context, TOKEN);
        return token;
    }

    public void setToken(Context context, String token) {
        saveString(context, TOKEN, token);
    }


    /*
    * 保存, 保存string和保存int类型的值
    * */
    public void saveString(Context context, String key, String text) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(key, text);
        editor.commit();
    }

    public void saveInt(Context context, String key, int value) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putInt(key, value);

        editor.commit();
    }

    /*
    * 获取, 获取string和获取int类型的值
    * */
    public String getStringValue(Context context, String key) {
        SharedPreferences settings;
        String text;

        settings = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        text = settings.getString(key, null);
        return text;
    }

    public int getIntValue(Context context, String key) {
        SharedPreferences settings;
        int value;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        value = settings.getInt(key, 0);
        return value;
    }


    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(Context context, String key) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.commit();
    }
}
