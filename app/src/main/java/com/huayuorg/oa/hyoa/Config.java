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
    public static final String ORIGINAL_TIME = "1111-01-01 01:01:01";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_NUMID="numid";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_MAC = "mac";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ALERT_AUDIT = "audit";
    public static final String KEY_ALERT_READ = "read";
    public static final String KEY_OFFICIAL_NOTICE = "notice";


    public static final String KEY_ALERT_NUMID = "numid";
    public static final String KEY_ALERT_ALERTTYPE = "alerttype";
    public static final String KEY_ALERT_BILLNUM = "billnum";
    public static final String KEY_ALERT_TITLE = "title";
    public static final String KEY_ALERT_LOOKPATH = "lookpath";
    public static final String KEY_ALERT_STATE = "state";
    public static final String KEY_ALERT_DEALTIME = "dealtime";
    public static final String KEY_ALERT_SYSCODE = "syscode";
    public static final String KEY_ALERT_SEQID = "seqid";
    public static final String KEY_ALERT_MAKETIME = "maketime";
    public static final String KEY_ALERT_NUMNAME = "numname";
    public static final String KEY_ALERT_BILLSTATE = "billstate";
    public static final String KEY_ALERT_ALERTTYPENAME = "alerttypename";

    //公文
    public static final String KEY_OFFICIAL = "official";
    public static final String KEY_OFFICIAL_BILLNUM = "billnum";
    public static final String KEY_OFFICIAL_TITLE = "title";
    public static final String KEY_OFFICIAL_TIME = "time";
    public static final String KEY_OFFICIAL_LINK = "link";
    public static final String KEY_OFFICIAL_TYPE = "type";
    public static final String KEY_OFFICIAL_NUMID = "numid";

    public static final String KEY_CONTACT = "contact";
    public static final String KEY_CONTACT_NUMID = "numid";
    public static final String KEY_CONTACT_ACCOUNT = "account";
    public static final String KEY_CONTACT_NUMNAME = "numname";
    public static final String KEY_CONTACT_SEX = "sex";
    public static final String KEY_CONTACT_MOBILE = "mobile";
    public static final String KEY_CONTACT_SHOWNAME = "showname";
    public static final String KEY_CONTACT_CORPID = "corpid";
    public static final String KEY_CONTACT_ORGID = "orgid";
    public static final String KEY_CONTACT_POSTID = "postid";
    public static final String KEY_CONTACT_NUMSTATE = "numstate";
    public static final String KEY_CONTACT_CORPORDER = "corporder";
    public static final String KEY_CONTACT_ORGANORDER = "organorder";
    public static final String KEY_CONTACT_POSTORDER = "postorder";
    public static final String KEY_CONTACT_BIRTHDAY = "birthday";
    public static final String KEY_CONTACT_PHOTOS = "photos";
    public static final String KEY_CONTACT_GROUPCORNET = "groupcornet";
    public static final String KEY_CONTACT_OFFICETEL = "officetel";
    public static final String KEY_CONTACT_ISMAINPOST = "ismainpost";
    public static final String KEY_CONTACT_ENTRYDATE = "entrydate";
    public static final String KEY_CONTACT_ISADMIN = "isadmin";
    public static final String KEY_CONTACT_IDNUMER = "idnumber";
    public static final String KEY_CONTACT_EMAIL = "email";
    public static final String KEY_CONTACT_CORPNAME = "corpname";
    public static final String KEY_CONTACT_ORGNAME = "orgname";
    public static final String KEY_CONTACT_POSTNAME = "postname";

    public static final String DB_DATABASE_NAME = "OA.db";
    public static final int DB_DATABASE_VERSION = 1;
    public static final String DB_CONTACT_TABLE_NAME = "tb_contact";
    public static final String DB_CONTACT_COLUMN_NUMID = "numid";
    public static final String DB_CONTACT_COLUMN_ACCOUNT = "account";
    public static final String DB_CONTACT_COLUMN_NUMNAME = "numname";
    public static final String DB_CONTACT_COLUMN_SEX = "sex";
    public static final String DB_CONTACT_COLUMN_MOBILE = "mobile";
    public static final String DB_CONTACT_COLUMN_SHOWNAME = "showname";
    public static final String DB_CONTACT_COLUMN_CORPID = "corpid";
    public static final String DB_CONTACT_COLUMN_ORGID = "orgid";
    public static final String DB_CONTACT_COLUMN_POSTID = "postid";
    public static final String DB_CONTACT_COLUMN_NUMSTATE = "numstate";
    public static final String DB_CONTACT_COLUMN_CORPORDER = "corporder";
    public static final String DB_CONTACT_COLUMN_ORGANORDER = "organorder";
    public static final String DB_CONTACT_COLUMN_POSTORDER = "postorder";
    public static final String DB_CONTACT_COLUMN_BIRTHDAY = "birthday";
    public static final String DB_CONTACT_COLUMN_PHOTOS = "photos";
    public static final String DB_CONTACT_COLUMN_GROUPCORNET = "groupcornet";
    public static final String DB_CONTACT_COLUMN_OFFICETEL = "officetel";
    public static final String DB_CONTACT_COLUMN_ISMAINPOST = "ismainpost";
    public static final String DB_CONTACT_COLUMN_ENTRYDATE = "entrydate";
    public static final String DB_CONTACT_COLUMN_ISADMIN = "isadmin";
    public static final String DB_CONTACT_COLUMN_IDNUMER = "idnumber";
    public static final String DB_CONTACT_COLUMN_EMAIL = "email";
    public static final String DB_CONTACT_COLUMN_CORPNAME = "corpname";
    public static final String DB_CONTACT_COLUMN_ORGNAME = "orgname";
    public static final String DB_CONTACT_COLUMN_POSTNAME = "postname";

    public static final String DB_OFFICIAL_TABLE_NAME = "tb_official";
    public static final String DB_OFFICIAL_COLUMN_TITLE = "title";
    public static final String DB_OFFICIAL_COLUMN_BILLNUM = "billnum";
    public static final String DB_OFFICIAL_COLUMN_LINK = "link";
    public static final String DB_OFFICIAL_COLUMN_TIME = "time";
    public static final String DB_OFFICIAL_COLUMN_TYPE = "type";
    public static final String DB_OFFICIAL_COLUMN_NUMID="numid";

    public static final String DB_ALERT_TABLE_NAME = "tb_alert";
    public static final String DB_ALERT_COLUMN_NUMID = "numid";
    public static final String DB_ALERT_COLUMN_ALERTTYPE = "alerttype";
    public static final String DB_ALERT_COLUMN_BILLNUM = "billnum";
    public static final String DB_ALERT_COLUMN_TITLE = "title";
    public static final String DB_ALERT_COLUMN_LOOKPATH = "lookpath";
    public static final String DB_ALERT_COLUMN_STATE = "state";
    public static final String DB_ALERT_COLUMN_DEALTIME = "dealtime";
    public static final String DB_ALERT_COLUMN_SYSCODE = "syscode";
    public static final String DB_ALERT_COLUMN_SEQID = "seqid";
    public static final String DB_ALERT_COLUMN_MAKETIME = "maketime";
    public static final String DB_ALERT_COLUMN_NUMNAME = "numname";
    public static final String DB_ALERT_COLUMN_BILLSTATE = "billstate";
    public static final String DB_ALERT_COLUMN_ALERTTYPENAME = "alerttypename";


    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 2;

    public static final String ACTION_LOGIN = "login";
    public static final String ACTION_LOAD_NOTICE = "notice";
    public static final String ACTION_LOAD_T0_BE_AUDIT = "audit";
    public static final String ACTION_LOAD_TO_BE_READ = "read";
    public static final String ACTION_LOAD_CONTACT = "contact";
    public static final String ACTION_LOAD_MAINPAGE = "mainpage";


    public static String getCacheToken(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKEN, "");
    }

    public static void cacheToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }
    public static String getNumid(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_NUMID, "");
    }

    public static void cacheNumid(Context context, String numid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NUMID, numid);
        editor.commit();
    }
    public static String getAccount(Context context) {
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_ACCOUNT, "");
    }

    public static void cacheAccount(Context context, String account) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ACCOUNT, account);
        editor.commit();
    }

    public static String getMac(Context context) {
        String macSerial = "-1";
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);


            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            macSerial = "-1";
            ex.printStackTrace();
        }
        return macSerial;

    }


}
