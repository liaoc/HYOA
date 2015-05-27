package com.huayuorg.oa.hyoa;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import com.huayuorg.oa.hyoa.atys.AtyLogin;
import com.huayuorg.oa.hyoa.atys.AtyMain;


public class BeforeLogin extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        String token = Config.getCacheToken(getApplicationContext());
        String account = Config.getAccount(getApplicationContext());
        if (token == "" || account == "") {
            startActivity(new Intent(BeforeLogin.this, AtyLogin.class));

        } else {
            startActivity(new Intent(BeforeLogin.this, AtyMain.class));
        }
        finish();
    }


}
