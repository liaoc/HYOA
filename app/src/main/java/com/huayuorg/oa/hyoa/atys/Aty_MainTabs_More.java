package com.huayuorg.oa.hyoa.atys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.data.UserDB;

public class Aty_MainTabs_More extends ActionBarActivity {
    TextView tvUserName;
    TextView tvUserPost;
    ImageView ivUserPhoto;
    Button btnLoginOut;
    UserDB userDB;
    SQLiteDatabase dbReader;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_main_tab_more);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        btnLoginOut = (Button) findViewById(R.id.btnLoginOut);
        ivUserPhoto= (ImageView) findViewById(R.id.ivUserPhoto);
        tvUserPost= (TextView) findViewById(R.id.tvUserPost);
        String userAccount = Config.getAccount(Aty_MainTabs_More.this);
        userDB = new UserDB(Aty_MainTabs_More.this);
        dbReader = userDB.getReadableDatabase();
        cursor = dbReader.rawQuery("select * from " + Config.DB_CONTACT_TABLE_NAME + " where " + Config.DB_CONTACT_COLUMN_ACCOUNT + "='" + userAccount+"' ", null);
        cursor.moveToFirst();
        String userPost = cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_SHOWNAME));
        int userSex=cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_SEX));
        String userName=cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_NUMNAME));
        if(userSex==0)
        {
            ivUserPhoto.setImageResource(R.drawable.contact_photo_male);
        }
        else
        {
            ivUserPhoto.setImageResource(R.drawable.contact_photo_female);
        }
        tvUserName.setText(userName + "(" + userAccount + ")");
        tvUserPost.setText(userPost);
        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.cacheAccount(Aty_MainTabs_More.this, "");
                Config.cacheToken(Aty_MainTabs_More.this, "");
                startActivity(new Intent(Aty_MainTabs_More.this, AtyLogin.class));
            }
        });

    }


}
