package com.huayuorg.oa.hyoa.frg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.adapter.AtyContactListAdapter;
import com.huayuorg.oa.hyoa.atys.AtyLogin;
import com.huayuorg.oa.hyoa.data.UserDB;
import com.huayuorg.oa.hyoa.entities.ContactInfo;
import com.huayuorg.oa.hyoa.net.GetContact;
import com.huayuorg.oa.hyoa.tool.RefreshableView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FrgMainTabContact extends Fragment {
    private AtyContactListAdapter atyContactListAdapter;
    private UserDB userDB;
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    private ListView lv;
    private EditText etSearch;
    RefreshableView refreshableView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frg_main_tab_contact, container, false);
        userDB = new UserDB(getActivity());
        atyContactListAdapter = new AtyContactListAdapter(getActivity());
       // setListAdapter(atyContactListAdapter);
        sqLiteDatabase = userDB.getReadableDatabase();
        cursor = sqLiteDatabase.query(Config.DB_CONTACT_TABLE_NAME, null, null, null, null, null, null);
        lv = (ListView) root.findViewById(R.id.lvPull);
        lv.setAdapter(atyContactListAdapter);
        //  System.out.println(cursor.getCount()+"");
        if (cursor.getCount() <= 0) {
            getContactData();
        } else {
            List<ContactInfo> data = userDB.getListContactInfo(cursor);
            atyContactListAdapter.clear();
            atyContactListAdapter.addAll(data);
        }


        etSearch = (EditText) root.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sqLiteDatabase = userDB.getReadableDatabase();
                String queryString = "select * from " + Config.DB_CONTACT_TABLE_NAME + " where (" + Config.DB_CONTACT_COLUMN_NUMNAME + " like '%" + etSearch.getText() + "%' or "
                        + Config.DB_CONTACT_COLUMN_ACCOUNT + " like '" + etSearch.getText() + "%' or "
                        + Config.DB_CONTACT_COLUMN_MOBILE + " like '%" + etSearch.getText() + "%'  or "
                        + Config.DB_CONTACT_COLUMN_GROUPCORNET + " like '%" + etSearch.getText() + "%' or  "
                        + Config.DB_CONTACT_COLUMN_OFFICETEL + " like '%" + etSearch.getText() + "%' ) ";

                //  cursor=sqLiteDatabase.query(Config.DB_CONTACT_TABLE_NAME, null, null, null, null, null, null);
                cursor = sqLiteDatabase.rawQuery(queryString, null);
                List<ContactInfo> data = userDB.getListContactInfo(cursor);
                atyContactListAdapter.clear();
                atyContactListAdapter.addAll(data);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        refreshableView = (RefreshableView) root.findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                new GetContact(Config.getAccount(getActivity()), Config.getCacheToken(getActivity()), new GetContact.SuccessCallback() {
                    @Override
                    public void onSuccess(List<ContactInfo> data) {
                        atyContactListAdapter.clear();
                        atyContactListAdapter.addAll(data);
                        userDB.SaveContactInfo(data);
                    }
                }, new GetContact.FailCallback() {
                    @Override
                    public void onFail(int errCode) {
                        if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                            startActivity(new Intent(getActivity(), AtyLogin.class));
                        } else {
                            Toast.makeText(getActivity(), R.string.fail_to_load, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                refreshableView.finishRefreshing();
            }
        }, 0);


        return root;
    }

    public void SelectDB() {
        cursor = sqLiteDatabase.query(Config.DB_CONTACT_TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        SelectDB();
    }

    private void getContactData() {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
        new GetContact(Config.getAccount(getActivity()), Config.getCacheToken(getActivity()), new GetContact.SuccessCallback() {
            @Override
            public void onSuccess(List<ContactInfo> data) {
                pd.dismiss();
                atyContactListAdapter.clear();
                atyContactListAdapter.addAll(data);
                userDB.SaveContactInfo(data);
            }
        }, new GetContact.FailCallback() {
            @Override
            public void onFail(int errCode) {
                pd.dismiss();
                if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(getActivity(), AtyLogin.class));
                } else {
                    Toast.makeText(getActivity(), R.string.fail_to_load, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
