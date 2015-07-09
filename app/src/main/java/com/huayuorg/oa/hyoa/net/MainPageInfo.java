package com.huayuorg.oa.hyoa.net;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.entities.Alert;
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
 * Created by Administrator on 2015/7/6.
 */
public class MainPageInfo {
    public MainPageInfo(String account, String token,String audittime,String readtime,String noticetime, final SuccessCallback successCallback, final FailCallBack failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null) {
                                List<Alert> dataAudit=new ArrayList<Alert>();
                                List<Alert> dataRead=new ArrayList<Alert>();
                                List<Official> dataNotice = new ArrayList<Official>();
                                JSONObject dataObj;
                                JSONArray jsonArray=jsonObject.getJSONArray(Config.KEY_ALERT_AUDIT);
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    dataObj = jsonArray.getJSONObject(i);
                                    String strDealDate = dataObj.getString(Config.KEY_ALERT_DEALTIME);
                                    String strMakeDate=dataObj.getString(Config.KEY_ALERT_MAKETIME);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date dealDate = new Date(),makeDate=new Date();
                                    try {
                                        dealDate = sdf.parse(strDealDate);
                                        makeDate=sdf.parse(strMakeDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    dataAudit.add(new Alert(dataObj.getInt(Config.KEY_ALERT_SEQID),
                                            dataObj.getString(Config.KEY_ALERT_BILLNUM),
                                            dataObj.getString(Config.KEY_ALERT_NUMID),
                                            dataObj.getInt(Config.KEY_ALERT_ALERTTYPE),
                                            dataObj.getString(Config.KEY_ALERT_TITLE),
                                            dataObj.getString(Config.KEY_ALERT_LOOKPATH),
                                            dataObj.getInt(Config.KEY_ALERT_STATE),
                                            dealDate,
                                            dataObj.getString(Config.KEY_ALERT_SYSCODE),
                                            makeDate,
                                            dataObj.getString(Config.KEY_ALERT_NUMNAME),
                                            dataObj.getInt(Config.KEY_ALERT_BILLSTATE),
                                            dataObj.getString(Config.KEY_ALERT_ALERTTYPENAME)
                                            ));
                                }

                                jsonArray=jsonObject.getJSONArray(Config.KEY_ALERT_READ);
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    dataObj = jsonArray.getJSONObject(i);
                                    String strDealDate = dataObj.getString(Config.KEY_ALERT_DEALTIME);
                                    String strMakeDate=dataObj.getString(Config.KEY_ALERT_MAKETIME);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date dealDate = new Date(),makeDate=new Date();
                                    try {
                                        dealDate = sdf.parse(strDealDate);
                                        makeDate=sdf.parse(strMakeDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    dataRead.add(new Alert(dataObj.getInt(Config.KEY_ALERT_SEQID),
                                            dataObj.getString(Config.KEY_ALERT_BILLNUM),
                                            dataObj.getString(Config.KEY_ALERT_NUMID),
                                            dataObj.getInt(Config.KEY_ALERT_ALERTTYPE),
                                            dataObj.getString(Config.KEY_ALERT_TITLE),
                                            dataObj.getString(Config.KEY_ALERT_LOOKPATH),
                                            dataObj.getInt(Config.KEY_ALERT_STATE),
                                            dealDate,
                                            dataObj.getString(Config.KEY_ALERT_SYSCODE),
                                            makeDate,
                                            dataObj.getString(Config.KEY_ALERT_NUMNAME),
                                            dataObj.getInt(Config.KEY_ALERT_BILLSTATE),
                                            dataObj.getString(Config.KEY_ALERT_ALERTTYPENAME)
                                    ));
                                }


                                 jsonArray = jsonObject.getJSONArray(Config.KEY_OFFICIAL_NOTICE);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    dataObj = jsonArray.getJSONObject(i);
                                    String strDate = dataObj.getString(Config.KEY_OFFICIAL_TIME);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date date = new Date();
                                    try {
                                        date = sdf.parse(strDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    dataNotice.add(new Official(dataObj.getString(Config.KEY_OFFICIAL_BILLNUM),
                                            dataObj.getString(Config.KEY_OFFICIAL_TITLE),
                                            dataObj.getString(Config.KEY_OFFICIAL_LINK),
                                            date,
                                            dataObj.getString(Config.KEY_OFFICIAL_TYPE),
                                            dataObj.getString(Config.KEY_OFFICIAL_NUMID)));
                                }
                              successCallback.onSuccess(dataAudit,dataRead,dataNotice);
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
        }, Config.KEY_ACTION, Config.ACTION_LOAD_MAINPAGE, Config.KEY_ACCOUNT, account, Config.KEY_TOKEN, token,Config.KEY_ALERT_AUDIT,audittime,Config.KEY_ALERT_READ,readtime,Config.KEY_OFFICIAL_NOTICE,noticetime);
    }


    public static interface SuccessCallback {
        void onSuccess(List<Alert> dataAudit,List<Alert> dataRead,List<Official> dataAlert);
    }

    public static interface FailCallBack {
        void onFail(int errCode);
    }
}
