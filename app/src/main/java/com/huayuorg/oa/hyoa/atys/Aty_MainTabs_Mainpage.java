package com.huayuorg.oa.hyoa.atys;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.adapter.AlertAdapter;
import com.huayuorg.oa.hyoa.adapter.NoticeAdapter;
import com.huayuorg.oa.hyoa.data.UserDB;
import com.huayuorg.oa.hyoa.entities.Alert;
import com.huayuorg.oa.hyoa.entities.ContactInfo;
import com.huayuorg.oa.hyoa.entities.Official;
import com.huayuorg.oa.hyoa.net.GetContact;
import com.huayuorg.oa.hyoa.net.MainPageInfo;

import java.text.SimpleDateFormat;
import java.util.List;

public class Aty_MainTabs_Mainpage extends TabActivity {
    //  TabsAdapter mTabsAdapter;
    private TabHost tabHost;
    UserDB userDB;
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase;
    PullToRefreshListView lvAudit, lvRead, lvNotice;
    NoticeAdapter adapterNotice;
    AlertAdapter adapterAudit, adapterRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty__main_tabs__mainpage);
        InitView();

        userDB = new UserDB(Aty_MainTabs_Mainpage.this);
        sqLiteDatabase = userDB.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery("select * from " + Config.DB_ALERT_TABLE_NAME+" where numid='"+Config.getNumid(Aty_MainTabs_Mainpage.this)+"'", null);
        if (cursor.getCount() <= 0) {
            final ProgressDialog pd = ProgressDialog.show(Aty_MainTabs_Mainpage.this, getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
            String strAuditTime = "1111-01-01 01:01:01", strReadTime = "1111-01-01 01:01:01", strNoticeTime = "1111-01-01 01:01:01";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (adapterAudit.getCount() > 0) {
                Alert alertAudit = (Alert) adapterAudit.getItem(0);
                strAuditTime = sdf.format(alertAudit.getDealtime());
            }
            if (adapterRead.getCount() > 0) {
                Alert alertRead = (Alert) adapterRead.getItem(0);
                strReadTime = sdf.format(alertRead.getDealtime());
            }
            if (adapterNotice.getCount() > 0) {
                Official officialNotice = (Official) adapterNotice.getItem(0);
                strNoticeTime = sdf.format(officialNotice.getTime());
            }
            new MainPageInfo(Config.getAccount(Aty_MainTabs_Mainpage.this), Config.getCacheToken(Aty_MainTabs_Mainpage.this), strAuditTime, strReadTime, strNoticeTime, new MainPageInfo.SuccessCallback() {
                @Override
                public void onSuccess(List<Alert> dataAudit, List<Alert> dataRead, List<Official> dataNotice) {
                    pd.dismiss();
                    adapterAudit.clear();
                    adapterNotice.clear();
                    adapterRead.clear();
                    adapterNotice.addAll(dataNotice);
                    adapterRead.addAll(dataRead);
                    adapterAudit.addAll(dataAudit);
                    userDB.SaveOfficialInfo(dataNotice);
                    userDB.SaveAlertInfo(dataRead, Config.KEY_ALERT_READ);
                    userDB.SaveAlertInfo(dataAudit, Config.KEY_ALERT_AUDIT);
                }
            }, new MainPageInfo.FailCallBack() {
                @Override
                public void onFail(int errCode) {
                    pd.dismiss();
                    if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                        startActivity(new Intent(Aty_MainTabs_Mainpage.this, AtyLogin.class));
                    } else {
                        Toast.makeText(Aty_MainTabs_Mainpage.this, R.string.fail_to_load, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
           getInfoFromDataBase();
            getInfoFromServer();
        }
    }

    private void getInfoFromServer() {
        String strAuditTime = "1111-01-01 01:01:01",
                strReadTime = "1111-01-01 01:01:01",
                strNoticeTime = "1111-01-01 01:01:01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (adapterAudit.getCount() > 0) {
            Alert alertAudit = (Alert) adapterAudit.getItem(0);
            strAuditTime = sdf.format(alertAudit.getDealtime());
        }
        if (adapterRead.getCount() > 0) {
            Alert alertRead = (Alert) adapterRead.getItem(0);
            strReadTime = sdf.format(alertRead.getDealtime());
        }
        if (adapterNotice.getCount() > 0) {
            Official officialNotice = (Official) adapterNotice.getItem(0);
            strNoticeTime = sdf.format(officialNotice.getTime());
        }
        new MainPageInfo(Config.getAccount(Aty_MainTabs_Mainpage.this), Config.getCacheToken(Aty_MainTabs_Mainpage.this), strAuditTime, strReadTime, strNoticeTime, new MainPageInfo.SuccessCallback() {
            @Override
            public void onSuccess(List<Alert> dataAudit, List<Alert> dataRead, List<Official> dataNotice) {
                userDB.SaveOfficialInfo(dataNotice);
                userDB.SaveAlertInfo(dataRead, Config.KEY_ALERT_READ);
                userDB.SaveAlertInfo(dataAudit, Config.KEY_ALERT_AUDIT);
                getInfoFromDataBase();

            }
        }, new MainPageInfo.FailCallBack() {
            @Override
            public void onFail(int errCode) {
                //   pd.dismiss();
                if (errCode == Config.RESULT_STATUS_INVALID_TOKEN) {
                    startActivity(new Intent(Aty_MainTabs_Mainpage.this, AtyLogin.class));
                } else {
                    Toast.makeText(Aty_MainTabs_Mainpage.this, R.string.fail_to_load, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getInfoFromDataBase() {
        sqLiteDatabase = userDB.getReadableDatabase();
        int auditItemCount = adapterAudit.getCount(), readItemCount = adapterRead.getCount(), noticeItemCount = adapterNotice.getCount();
        cursor = sqLiteDatabase.rawQuery("select * from " + Config.DB_OFFICIAL_TABLE_NAME + " where numid='"+Config.getNumid(Aty_MainTabs_Mainpage.this)+"' order by " + Config.DB_OFFICIAL_COLUMN_TIME + " desc limit 0," + (noticeItemCount + 10), null);
        List<Official> dataNotice = userDB.getListOfficialInfo(cursor);
        adapterNotice.clear();
        adapterNotice.addAll(dataNotice);

        cursor = sqLiteDatabase.rawQuery("select * from " + Config.DB_ALERT_TABLE_NAME + " where numid='"+Config.getNumid(Aty_MainTabs_Mainpage.this)+"' and " + Config.DB_ALERT_COLUMN_ALERTTYPE + " in (1,2,6,7,8,63,64) order by " + Config.DB_ALERT_COLUMN_STATE + " desc," + Config.DB_ALERT_COLUMN_DEALTIME + " desc limit 0," + (auditItemCount + 10), null);
        List<Alert> dataAudit = userDB.getListAlertInfo(cursor);
        adapterAudit.clear();
        adapterAudit.addAll(dataAudit);

        cursor = sqLiteDatabase.rawQuery("select * from " + Config.DB_ALERT_TABLE_NAME + " where numid='"+Config.getNumid(Aty_MainTabs_Mainpage.this)+"' and " + Config.DB_ALERT_COLUMN_ALERTTYPE + " in (3,4,5,9,10) order by " + Config.DB_ALERT_COLUMN_STATE + " desc," + Config.DB_ALERT_COLUMN_DEALTIME + " desc limit 0," + (readItemCount + 10), null);
        List<Alert> dataRead = userDB.getListAlertInfo(cursor);
        adapterRead.clear();
        adapterRead.addAll(dataRead);
    }

    private void InitView() {
        tabHost = this.getTabHost();
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("待审事项").setContent(R.id.frg_to_be_audit));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("待阅事项").setContent(R.id.frg_to_be_read));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator("通知").setContent(R.id.frg_to_be_notify));


        lvRead = (PullToRefreshListView) findViewById(R.id.lvRead);
        lvAudit = (PullToRefreshListView) findViewById(R.id.lvAudit);
        lvNotice = (PullToRefreshListView) findViewById(R.id.lvNotify);
        lvRead.setMode(PullToRefreshBase.Mode.BOTH);
        lvAudit.setMode(PullToRefreshBase.Mode.BOTH);
        lvNotice.setMode(PullToRefreshBase.Mode.BOTH);
        adapterAudit = new AlertAdapter(this);
        adapterNotice = new NoticeAdapter(this);
        adapterRead = new AlertAdapter(this);
        lvAudit.setAdapter(adapterAudit);
        lvRead.setAdapter(adapterRead);
        lvNotice.setAdapter(adapterNotice);
        lvAudit.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(List_Action.PULL_DOWN).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(List_Action.PULL_UP).execute();

            }
        });
        lvRead.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(List_Action.PULL_DOWN).execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(List_Action.PULL_UP).execute();

            }
        });
        lvNotice.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(List_Action.PULL_DOWN).execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask(List_Action.PULL_UP).execute();

            }
        });
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_aty__main_tabs__mainpage, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    private class GetDataTask extends AsyncTask<Void, Void, String> {//定义返回值的类型

        List_Action list_action;

        public GetDataTask(List_Action list_action) {
            this.list_action = list_action;
        }

        // 后台处理部分
        @Override
        protected String doInBackground(Void... params) {

            if (list_action == List_Action.PULL_DOWN) {
                getInfoFromServer();
            }
            String str = "Added after refresh...I add";
            return str;
        }

        @Override
        protected void onPostExecute(String result) {

            if(list_action==List_Action.PULL_UP)
            {
                getInfoFromDataBase();
            }
            lvAudit.onRefreshComplete();
           lvNotice.onRefreshComplete();
           lvRead.onRefreshComplete();
            super.onPostExecute(result);//这句是必有的，AsyncTask规定的格式
        }
    }

    public enum List_Action {
        PULL_UP, PULL_DOWN
    }
}
