package com.huayuorg.oa.hyoa.atys;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.huayuorg.oa.hyoa.R;

public class Aty_Main extends TabActivity implements View.OnClickListener {
    private LinearLayout mTabBtnMainPage;
    private LinearLayout mTabBtnContacts;
    private LinearLayout mTabBtnWorkFlow;
    private LinearLayout mTabBtnMore;
    TextView tvTitle;
    private TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        tabHost = this.getTabHost();
        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("1").setContent(new Intent(this, Aty_MainTabs_Mainpage.class)));
        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("2").setContent(new Intent(this, Aty_MainTabs_Contact.class)));
        tabHost.addTab(tabHost.newTabSpec("3").setIndicator("3").setContent(new Intent(this, Aty_MainTabs_WorkFlow.class)));
        tabHost.addTab(tabHost.newTabSpec("4").setIndicator("4").setContent(new Intent(this, Aty_MainTabs_More.class)));

        initView();
    }
    private void initView() {
        mTabBtnMainPage = (LinearLayout) findViewById(R.id.id_tab_bottom_mainpage);
        mTabBtnContacts = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
        mTabBtnWorkFlow = (LinearLayout) findViewById(R.id.id_tab_bottom_workflow);
        mTabBtnMore = (LinearLayout) findViewById(R.id.id_tab_bottom_more);

        mTabBtnMainPage.setOnClickListener(this);
        mTabBtnContacts.setOnClickListener(this);
        mTabBtnWorkFlow.setOnClickListener(this);
        mTabBtnMore.setOnClickListener(this);
        mTabBtnMainPage.setBackgroundColor(Color.parseColor("#377AB1"));

        tvTitle= (TextView) findViewById(R.id.tvBarTitle);
    }

    protected void resetTabBtn() {
        mTabBtnMainPage.setBackgroundColor(Color.parseColor("#26537A"));
        mTabBtnContacts.setBackgroundColor(Color.parseColor("#26537A"));
        mTabBtnWorkFlow.setBackgroundColor(Color.parseColor("#26537A"));
        mTabBtnMore.setBackgroundColor(Color.parseColor("#26537A"));
    }

    @Override
    public void onClick(View v) {
        resetTabBtn();
        switch (v.getId()) {
            case R.id.id_tab_bottom_mainpage:
                mTabBtnMainPage.setBackgroundColor(Color.parseColor("#377AB1"));
                tabHost.setCurrentTabByTag("1");
                tvTitle.setText(R.string.main_page);
                break;
            case R.id.id_tab_bottom_contact:
                mTabBtnContacts.setBackgroundColor(Color.parseColor("#377AB1"));
                tabHost.setCurrentTabByTag("2");
               tvTitle.setText(R.string.contact_book);
                break;
            case R.id.id_tab_bottom_workflow:
                mTabBtnWorkFlow.setBackgroundColor(Color.parseColor("#377AB1"));
                tabHost.setCurrentTabByTag("3");
                tvTitle.setText(R.string.flow_center);
                break;
            case R.id.id_tab_bottom_more:
                mTabBtnMore.setBackgroundColor(Color.parseColor("#377AB1"));
                tabHost.setCurrentTabByTag("4");
               tvTitle.setText(R.string.more_information);
                break;
        }
    }

}
