package com.huayuorg.oa.hyoa.atys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.frg.FrgMainTab01;
import com.huayuorg.oa.hyoa.frg.FrgMainTab02;
import com.huayuorg.oa.hyoa.frg.FrgMainTab03;
import com.huayuorg.oa.hyoa.frg.FrgMainTab04;

import java.util.ArrayList;
import java.util.List;

public class AtyMain extends FragmentActivity implements View.OnClickListener {

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
                        ((ImageButton) mTabBtnMainPage.findViewById(R.id.btn_tab_bottom_mainpage))
                                .setImageResource(R.drawable.tab_mainpage_pressed);
                        break;
                    case 1:
                        ((ImageButton) mTabBtnContacts.findViewById(R.id.btn_tab_bottom_contact))
                                .setImageResource(R.drawable.tab_contact_pressed);
                        break;
                    case 2:
                        ((ImageButton) mTabBtnWorkFlow.findViewById(R.id.btn_tab_bottom_workflow))
                                .setImageResource(R.drawable.tab_workflow_pressed);
                        break;
                    case 3:
                        ((ImageButton) mTabBtnMore.findViewById(R.id.btn_tab_bottom_more))
                                .setImageResource(R.drawable.tab_more_pressed);
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
    }

    private void initView() {
        mTabBtnMainPage = (LinearLayout) findViewById(R.id.id_tab_bottom_mainpage);
        mTabBtnContacts = (LinearLayout) findViewById(R.id.id_tab_bottom_contact);
        mTabBtnWorkFlow = (LinearLayout) findViewById(R.id.id_tab_bottom_workflow);
        mTabBtnMore = (LinearLayout) findViewById(R.id.id_tab_bottom_more);

        FrgMainTab01 tab01 = new FrgMainTab01();
        FrgMainTab02 tab02 = new FrgMainTab02();
        FrgMainTab03 tab03 = new FrgMainTab03();
        FrgMainTab04 tab04 = new FrgMainTab04();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mFragments.add(tab03);
        mFragments.add(tab04);
    }

    protected void resetTabBtn() {
        ((ImageButton) mTabBtnMainPage.findViewById(R.id.btn_tab_bottom_mainpage))
                .setImageResource(R.drawable.tab_mainpage_normal);
        ((ImageButton) mTabBtnContacts.findViewById(R.id.btn_tab_bottom_contact))
                .setImageResource(R.drawable.tab_contact_normal);
        ((ImageButton) mTabBtnWorkFlow.findViewById(R.id.btn_tab_bottom_workflow))
                .setImageResource(R.drawable.tab_workflow_normal);
        ((ImageButton) mTabBtnMore.findViewById(R.id.btn_tab_bottom_more))
                .setImageResource(R.drawable.tab_more_normal);
    }

    @Override
    public void onClick(View v) {
        resetTabBtn();
        switch (v.getId()) {
            case R.id.id_tab_bottom_mainpage:
                ((ImageButton) mTabBtnMainPage.findViewById(R.id.btn_tab_bottom_mainpage))
                        .setImageResource(R.drawable.tab_mainpage_pressed);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_tab_bottom_contact:
                ((ImageButton) mTabBtnContacts.findViewById(R.id.btn_tab_bottom_contact))
                        .setImageResource(R.drawable.tab_contact_pressed);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_tab_bottom_workflow:
                ((ImageButton) mTabBtnWorkFlow.findViewById(R.id.btn_tab_bottom_workflow))
                        .setImageResource(R.drawable.tab_workflow_pressed);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_tab_bottom_more:
                ((ImageButton) mTabBtnMore.findViewById(R.id.btn_tab_bottom_more))
                        .setImageResource(R.drawable.tab_more_pressed);
                mViewPager.setCurrentItem(3, false);
                break;

        }
    }
}
