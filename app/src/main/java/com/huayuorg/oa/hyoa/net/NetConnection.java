package com.huayuorg.oa.hyoa.net;

import android.os.AsyncTask;

import com.huayuorg.oa.hyoa.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015/5/22.
 */
public class NetConnection {
    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final failCallBack failCallBack, final String... kvs) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paramBuffer = new StringBuffer();
                for (int i = 0; i < kvs.length; i += 2) {
                    paramBuffer.append(kvs[i]).append('=').append(kvs[i + 1]).append('&');
                }
                try {
                    URLConnection uc;
                    System.out.println("URL: " + url);
                    System.out.println("Data: " + paramBuffer.toString());
                    switch (method) {
                        case POST:
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHAR_SET));
                            bw.write(paramBuffer.toString());
                            bw.flush();
                            break;
                        default:
                            uc = new URL(url + "?" + paramBuffer).openConnection();
                            break;
                    }

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uc.getInputStream(), Config.CHAR_SET));
                    String line = "";
                    StringBuffer result = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }
                    System.out.println("Result:" + result.toString());
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                   if(successCallback!=null){
                       successCallback.onSuccess(s);
                   }
                }else
                {
                    if(failCallBack!=null)
                    {
                        failCallBack.onFail();
                    }
                }
            }
        }.execute();
    }

    public static interface SuccessCallback {
        void onSuccess(String result);
    }

    public static interface failCallBack {
        void onFail();
    }
}
