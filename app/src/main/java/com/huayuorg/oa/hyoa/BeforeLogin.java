package com.huayuorg.oa.hyoa;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import com.huayuorg.oa.hyoa.atys.AtyLogin;

import com.huayuorg.oa.hyoa.atys.Aty_Main;


public class BeforeLogin extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
//        startActivity(new Intent(BeforeLogin.this,MainActivity.class));
//        finish();
        String token = Config.getCacheToken(getApplicationContext());
        String account = Config.getAccount(getApplicationContext());
        if (token == "" || account == "") {
            startActivity(new Intent(BeforeLogin.this, AtyLogin.class));

        } else {
            startActivity(new Intent(BeforeLogin.this, Aty_Main.class));
        }
        finish();
    }


}
