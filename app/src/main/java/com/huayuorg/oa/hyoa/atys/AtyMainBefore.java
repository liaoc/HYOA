package com.huayuorg.oa.hyoa.atys;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.frg.FrgMainTabMainPage;
import com.huayuorg.oa.hyoa.frg.FrgMainTabContact;
import com.huayuorg.oa.hyoa.frg.FrgMainTab03;
import com.huayuorg.oa.hyoa.frg.FrgMainTabMore;

import java.util.ArrayList;
import java.util.List;

public class AtyMainBefore extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();


    /**
     * 底部四个按钮
     */
    private LinearLayout mTabBtnMainPage;
    private LinearLayout mTabBtnContacts;
    private LinearLayout mTabBtnWorkFlow;
    private LinearLayout mTabBtnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_main);


        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        initView();
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private int currentIndex;

            @Override
            public void onPageSelected(int position) {
                resetTabBtn();
                switch (position) {
                    case 0:
                        mTabBtnMainPage.setBackgroundColor(Color.parseColor("#377AB1"));
                        break;
                    case 1:
                        mTabBtnContacts.setBackgroundColor(Color.parseColor("#377AB1"));
                        break;
                    case 2:
                        mTabBtnWorkFlow.setBackgroundColor(Color.parseColor("#377AB1"));
                        break;
                    case 3:
                        mTabBtnMore.setBackgroundColor(Color.parseColor("#377AB1"));
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        mTabBtnMainPage.setOnClickListener(this);
        mTabBtnContacts.setOnClickListener(this);
        mTabBtnWorkFlow.setOnClickListener(this);
        mTabBtnMore.setOnClickListener(this);
        mTabBtnMainPage.setBackgroundColor(Color.parseColor("#377AB1"));
    }

    private void initView() {
        mTabBtnMainPage = (LinearLayout) findViewById(R.id.id_tab_bottom_mainpage);
        mTabBtnContacts = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
        mTabBtnWorkFlow = (LinearLayout) findViewById(R.id.id_tab_bottom_workflow);
        mTabBtnMore = (LinearLayout) findViewById(R.id.id_tab_bottom_more);

        FrgMainTabMainPage tab01 = new FrgMainTabMainPage();
        FrgMainTabContact tab02 = new FrgMainTabContact();
        FrgMainTab03 tab03 = new FrgMainTab03();
        FrgMainTabMore tab04 = new FrgMainTabMore();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mFragments.add(tab03);
        mFragments.add(tab04);
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
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_tab_bottom_contact:
                 mTabBtnContacts.setBackgroundColor(Color.parseColor("#377AB1"));
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_tab_bottom_workflow:
                mTabBtnWorkFlow.setBackgroundColor(Color.parseColor("#377AB1"));
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_tab_bottom_more:
               mTabBtnMore.setBackgroundColor(Color.parseColor("#377AB1"));
                mViewPager.setCurrentItem(3, false);
                break;

        }
    }
}
