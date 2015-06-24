package com.huayuorg.oa.hyoa;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by Administrator on 2015/5/22.
 */
public class Config {
    public static final String SERVER_URL = "http://172.18.1.208:8093/test.ashx";
    public static final String APP_ID = "com.huayuorg.com";
    public static final String CHAR_SET = "UTF-8";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_ACTION="action";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_MAC="mac";
    public static final String KEY_STATUS="status";

    public static final int RESULT_STATUS_SUCCESS=1;
    public static final int RESULT_STATUS_FAIL=0;
    public static final int RESULT_STATUS_INVALID_TOKEN=2;

    public static final String ACTION_LOGIN="login";


    public static String getCacheToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, "");
    }

    public static void cacheToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public static String getAccount(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_ACCOUNT, "");
    }

    public static void cacheAccount(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ACCOUNT, token);
        editor.commit();
    }

    public static String getMac(Context context) {
        String macSerial="-1";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);


            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            macSerial="-1";
            ex.printStackTrace();
        }
        return macSerial;

    }
}
