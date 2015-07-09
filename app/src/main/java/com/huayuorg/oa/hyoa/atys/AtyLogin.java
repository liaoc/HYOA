package com.huayuorg.oa.hyoa.atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.net.Login;


public class AtyLogin extends Activity {

    EditText etAccount, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_aty__login);
        setContentView(R.layout.aty_login);
        etAccount = (EditText) findViewById(R.id.etAccount);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                 btnLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_corner_button_action_down));
                }
                else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    btnLogin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_corner_button_action_up));

                }
                return false;
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etAccount.getText().length() == 0 || etPassword.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.account_or_password_not_enter), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    final ProgressDialog progressDialog = ProgressDialog.show(AtyLogin.this, getString(R.string.connecting), getString(R.string.connecting_to_server));
                    new Login(Config.getMac(getApplicationContext()), etAccount.getText().toString(),
                            etPassword.getText().toString(), new Login.SuccessCallback() {
                        @Override
                        public void onSuccess(String token,String numid) {
                            progressDialog.dismiss();
                            Config.cacheToken(getApplicationContext(), token);
                            Config.cacheAccount(AtyLogin.this, etAccount.getText().toString());
                            Config.cacheNumid(AtyLogin.this,numid);
                            Intent i = new Intent(getApplicationContext(),Aty_Main.class);
                            i.putExtra(Config.KEY_TOKEN, token);
                            i.putExtra(Config.KEY_ACCOUNT, etAccount.getText().toString());

                            startActivity(i);
                            finish();
                        }
                    }, new Login.FailCallback() {
                        @Override
                        public void onFail(int status) {
                            progressDialog.dismiss();
                            if (status == 0) {
                                Toast.makeText(getApplicationContext(), getString(R.string.fail_to_login), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.check_account_and_password), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }


}
