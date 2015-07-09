package com.huayuorg.oa.hyoa.atys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.adapter.AtyContactListAdapter;
import com.huayuorg.oa.hyoa.data.UserDB;
import com.huayuorg.oa.hyoa.entities.ContactInfo;
import com.huayuorg.oa.hyoa.net.GetContact;
import com.huayuorg.oa.hyoa.tool.RefreshableView;

import java.util.List;

public class Aty_MainTabs_Contact extends ActionBarActivity {
    private AtyContactListAdapter atyContactListAdapter;
    private UserDB userDB;
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    private PullToRefreshListView lv;
    private EditText etSearch;
    private PopupWindow mPop;

    //  RefreshableView refreshableView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty__main_tabs__contact);
        userDB = new UserDB(Aty_MainTabs_Contact.this);
        atyContactListAdapter = new AtyContactListAdapter(Aty_MainTabs_Contact.this);
        lv = (PullToRefreshListView) findViewById(R.id.lvPull);
        lv.setAdapter(atyContactListAdapter);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                //   Toast.makeText(Aty_MainTabs_Contact.this,"load complete",Toast.LENGTH_SHORT).show();
                //   refreshView.onRefreshComplete();
                //  lv.onRefreshComplete();
                new GetDataTask().execute();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo contactInfo = (ContactInfo) atyContactListAdapter.getItem(position - 1);
                Toast.makeText(Aty_MainTabs_Contact.this, contactInfo.getNumname(), Toast.LENGTH_SHORT).show();
            }
        });

        sqLiteDatabase = userDB.getReadableDatabase();
        cursor = sqLiteDatabase.query(Config.DB_CONTACT_TABLE_NAME, null, null, null, null, null, null);

        //  System.out.println(cursor.getCount()+"");
        if (cursor.getCount() <= 0) {
            getContactData();
        } else {
            List<ContactInfo> data = userDB.getListContactInfo(cursor);
            atyContactListAdapter.clear();
            atyContactListAdapter.addAll(data);
        }


        etSearch = (EditText) findViewById(R.id.etSearch);
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

//        refreshableView = (RefreshableView)findViewById(R.id.refreshable_view);
//        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new GetContact(Config.getAccount(Aty_MainTabs_Contact.this), Config.getCacheToken(Aty_MainTabs_Contact.this), new GetContact.SuccessCallback() {
//                    @Override
//                    public void onSuccess(List<ContactInfo> data) {
//                        atyContactListAdapter.clear();
//                        atyContactListAdapter.addAll(data);
//                        userDB.SaveContactInfo(data);
//                    }
//                }, new GetContact.FailCallback() {
//                    @Override
//                    public void onFail(int errCode) {
//                        if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
//                            startActivity(new Intent(Aty_MainTabs_Contact.this, AtyLogin.class));
//                        } else {
//                            Toast.makeText(Aty_MainTabs_Contact.this, R.string.fail_to_load, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                refreshableView.finishRefreshing();
//            }
//        }, 0);

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
        final ProgressDialog pd = ProgressDialog.show(Aty_MainTabs_Contact.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
        new GetContact(Config.getAccount(Aty_MainTabs_Contact.this), Config.getCacheToken(Aty_MainTabs_Contact.this), new GetContact.SuccessCallback() {
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
                    startActivity(new Intent(Aty_MainTabs_Contact.this, AtyLogin.class));
                } else {
                    Toast.makeText(Aty_MainTabs_Contact.this, R.string.fail_to_load, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {//定义返回值的类型

        // 后台处理部分
        @Override
        protected String doInBackground(Void... params) {
            new GetContact(Config.getAccount(Aty_MainTabs_Contact.this), Config.getCacheToken(Aty_MainTabs_Contact.this), new GetContact.SuccessCallback() {
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
                        startActivity(new Intent(Aty_MainTabs_Contact.this, AtyLogin.class));
                    } else {
                        Toast.makeText(Aty_MainTabs_Contact.this, R.string.fail_to_load, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            String str = "Added after refresh...I add";
            return str;
        }

        @Override
        protected void onPostExecute(String result) {

            lv.onRefreshComplete();

            super.onPostExecute(result);//这句是必有的，AsyncTask规定的格式
        }
    }
}
