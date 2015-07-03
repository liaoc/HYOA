package com.huayuorg.oa.hyoa.net;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.entities.ContactInfo;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/30.
 */
public class GetContact  {
    public GetContact(String account,String token,final SuccessCallback successCallback,final FailCallback failCallback)
    {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS))
                    {
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallback!=null) {
                                JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_CONTACT);
                                JSONObject object;
                                List<ContactInfo> data = new ArrayList<ContactInfo>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    object = jsonArray.getJSONObject(i);
                                    Date dateEntrydate=new Date();
                                    Date dateBirthday=new Date();
                                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                    try {
                                        dateEntrydate=sdf.parse(object.getString(Config.KEY_CONTACT_ENTRYDATE));
                                        dateBirthday=sdf.parse(object.getString(Config.KEY_CONTACT_BIRTHDAY));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    data.add(new ContactInfo(object.getString(Config.KEY_CONTACT_NUMID),
                                            object.getString(Config.KEY_CONTACT_ACCOUNT),
                                            object.getString(Config.KEY_CONTACT_NUMNAME),
                                            object.getString(Config.KEY_CONTACT_MOBILE),
                                            object.getInt(Config.KEY_CONTACT_SEX),
                                            object.getString(Config.KEY_CONTACT_SHOWNAME),
                                            object.getString(Config.KEY_CONTACT_CORPID),
                                            object.getString(Config.KEY_CONTACT_ORGID),
                                            object.getString(Config.KEY_CONTACT_POSTID),
                                            object.getInt(Config.KEY_CONTACT_NUMSTATE),
                                            object.getInt(Config.KEY_CONTACT_CORPORDER),
                                            object.getInt(Config.KEY_CONTACT_ORGANORDER),
                                            object.getInt(Config.KEY_CONTACT_POSTORDER),
                                            dateBirthday,
                                            object.getString(Config.KEY_CONTACT_PHOTOS),
                                            object.getString(Config.KEY_CONTACT_GROUPCORNET),
                                            object.getString(Config.KEY_CONTACT_OFFICETEL),
                                            object.getInt(Config.KEY_CONTACT_ISMAINPOST),
                                            dateEntrydate,
                                            object.getInt(Config.KEY_CONTACT_ISADMIN),
                                            object.getString(Config.KEY_CONTACT_IDNUMER),
                                            object.getString(Config.KEY_CONTACT_EMAIL),
                                            object.getString(Config.KEY_CONTACT_CORPNAME),
                                            object.getString(Config.KEY_CONTACT_ORGNAME),
                                            object.getString(Config.KEY_CONTACT_POSTNAME)));
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
        },Config.KEY_ACTION,Config.ACTION_LOAD_CONTACT,Config.KEY_ACCOUNT,account,Config.KEY_TOKEN,token);
    }
    public static interface SuccessCallback{
        void onSuccess(List<ContactInfo> data);
    }
    public static interface FailCallback{
        void onFail(int errCode);
    }
}
