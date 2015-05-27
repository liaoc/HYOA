package com.huayuorg.oa.hyoa;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Config {
    public static final String APP_ID = "com.huayuorg.com";
    public static final String CHAR_SET = "UTF-8";
    public  static final String SERVER_URL="";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACCOUNT = "account";

    public String getCacheToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, "");
    }

    public void cacheToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }
    public String getAccount(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_ACCOUNT, "");
    }

    public void cacheAccount(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ACCOUNT, token);
        editor.commit();
    }
}
