package com.huayuorg.oa.hyoa.net;

import com.huayuorg.oa.hyoa.Config;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/5/26.
 */
public class Login {
    public Login(String phone_mac, String account, String password, final SuccessCallback successCallback, final FailCallback failCallback) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallback != null) {
                                successCallback.onSuccess(obj.getString(Config.KEY_TOKEN));
                            }
                            break;
                        default:
                            if (failCallback != null) {
                                failCallback.onFail(obj.getInt(Config.KEY_STATUS));
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback != null) {
                        failCallback.onFail(0);
                    }
                }

            }
        }, new NetConnection.failCallBack() {
            @Override
            public void onFail() {
                if (failCallback != null) {
                    failCallback.onFail(0);
                }

            }
        }, Config.KEY_ACTION, Config.ACTION_LOGIN, Config.KEY_MAC,phone_mac,Config.KEY_ACCOUNT, account, Config.KEY_PASSWORD, password);
    }

    public static interface SuccessCallback {
        void onSuccess(String token);
    }

    public static interface FailCallback {
        void onFail(int state);
    }
}
