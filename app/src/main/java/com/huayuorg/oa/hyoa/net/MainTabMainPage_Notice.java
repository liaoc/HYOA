package com.huayuorg.oa.hyoa.net;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.entities.Official;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class MainTabMainPage_Notice {
    public MainTabMainPage_Notice(String account, String token, final SuccessCallback successCallback, final FailCallBack failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null) {
                                List<Official> data = new ArrayList<Official>();
                                JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_OFFICIAL);
                                JSONObject dataObj;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    dataObj = jsonArray.getJSONObject(i);
                                    String strDate = dataObj.getString(Config.KEY_OFFICIAL_TIME);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = new Date();
                                    try {
                                        date = sdf.parse(strDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    data.add(new Official(dataObj.getString(Config.KEY_OFFICIAL_BILLNUM),
                                            dataObj.getString(Config.KEY_OFFICIAL_TITLE),
                                            date,
                                            dataObj.getString(Config.KEY_OFFICIAL_LINK)));
                                }
                                successCallback.onSuccess(data);
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallback != null) {
                                failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null) {
                        failCallback.onFail(Config.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.failCallBack() {
            @Override
            public void onFail() {
                if (failCallback != null) {
                    failCallback.onFail(Config.RESULT_STATUS_FAIL);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_LOAD_NOTICE, Config.KEY_ACCOUNT, account, Config.KEY_TOKEN, token);
    }

    public static interface SuccessCallback {
        void onSuccess(List<Official> data);
    }

    public static interface FailCallBack {
        void onFail(int errCode);
    }
}
