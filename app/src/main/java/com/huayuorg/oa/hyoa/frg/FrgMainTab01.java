package com.huayuorg.oa.hyoa.frg;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.huayuorg.oa.hyoa.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FrgMainTab01 extends Fragment implements View.OnClickListener {
    private TextView text1, text2;
    private ViewPager vPager = null;
    private static final int TAB_COUNT = 3;
    private int current_index = 0;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frg_main_tab_01, container, false);
        vPager = (ViewPager) root.findViewById(R.id.vPager);
        text1 = (TextView) root.findViewById(R.id.text1);
        text2 = (TextView) root.findViewById(R.id.text2);
        final TextView[] titles = {text1, text2};
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        FrgToBeAudit tab01 = new FrgToBeAudit();
        FrgToBeRead tab02 = new FrgToBeRead();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        vPager.setAdapter(mAdapter);
        vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        return root;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                vPager.setCurrentItem(0, false);
                break;
            case R.id.text2:
                vPager.setCurrentItem(1, false);
                break;
        }
    }
}
