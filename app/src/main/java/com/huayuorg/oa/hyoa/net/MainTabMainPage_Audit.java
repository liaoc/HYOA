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
 * Created by Administrator on 2015/6/29.
 */
public class MainTabMainPage_Audit {
    public MainTabMainPage_Audit(String account, String token, final SuccessCallback successCallback, final FailCallback failCallback){
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS))
                    {
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null) {
                                JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_OFFICIAL);
                                JSONObject object;
                                List<Official> data = new ArrayList<Official>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    object = jsonArray.getJSONObject(i);
                                    Date date=new Date();
                                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        date=sdf.parse(object.getString(Config.KEY_OFFICIAL_TIME));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    data.add(new Official(object.getString(Config.KEY_OFFICIAL_BILLNUM),
                                            object.getString(Config.KEY_OFFICIAL_TITLE),date,
                                            object.getString(Config.KEY_OFFICIAL_LINK)));
                                }
                                successCallback.onSuccess(data);
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if(failCallback!=null)
                            {
                                failCallback.onFail(Config.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if(failCallback!=null){
                                failCallback.onFail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    if(failCallback!=null){
                        failCallback.onFail(Config.RESULT_STATUS_FAIL);
                    }
                    e.printStackTrace();
                }
            }
        }, new NetConnection.failCallBack() {
            @Override
            public void onFail() {

            }
        },Config.KEY_ACTION,Config.ACTION_LOAD_T0_BE_AUDIT,Config.KEY_ACCOUNT,account,Config.KEY_TOKEN,token);
    }
    public static interface SuccessCallback{
        void onSuccess(List<Official> data);
    }
    public static interface FailCallback{
        void onFail(int errCode);
    }
}
