package com.huayuorg.oa.hyoa.atys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;

public class AtyMain extends Activity {

    TextView tvHello;
    Button btnClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        tvHello = (TextView) findViewById(R.id.tvHello);
        tvHello.setText("hello "+Config.getAccount(this));
        btnClean= (Button) findViewById(R.id.btnClean);
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             SharedPreferences.Editor editor= getSharedPreferences(Config.APP_ID,MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),"clear done",Toast.LENGTH_LONG).show();
            }
        });
    }

}
