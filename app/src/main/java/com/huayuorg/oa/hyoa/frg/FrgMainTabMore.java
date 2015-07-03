package com.huayuorg.oa.hyoa.frg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.atys.AtyLogin;
import com.huayuorg.oa.hyoa.data.UserDB;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FrgMainTabMore extends Fragment {
    TextView tvUserName;
    TextView tvUserPost;
    ImageView ivUserPhoto;
    Button btnLoginOut;
    UserDB userDB;
    SQLiteDatabase dbReader;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frg_main_tab_more, container, false);
        tvUserName = (TextView) root.findViewById(R.id.tvUserName);
        btnLoginOut = (Button) root.findViewById(R.id.btnLoginOut);
        ivUserPhoto= (ImageView) root.findViewById(R.id.ivUserPhoto);
        tvUserPost= (TextView) root.findViewById(R.id.tvUserPost);
        String userAccount = Config.getAccount(getActivity());
        userDB = new UserDB(getActivity());
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
                Config.cacheAccount(getActivity(), "");
                Config.cacheToken(getActivity(), "");
                startActivity(new Intent(getActivity(), AtyLogin.class));
            }
        });
        return root;
    }
}
