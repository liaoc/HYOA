package com.huayuorg.oa.hyoa.frg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.adapter.FrgMainTab01ListAdapter;
import com.huayuorg.oa.hyoa.atys.AtyLogin;
import com.huayuorg.oa.hyoa.net.MainTabMainPage_Notice;
import com.huayuorg.oa.hyoa.entities.Official;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FrgMainTabMainPage extends android.support.v4.app.ListFragment implements View.OnClickListener {
    private TextView tvTabAuditLabel, tvTabReadLabel;
    private ViewPager vPager = null;
    private int current_index = 0;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private FrgMainTab01ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frg_main_tab_main_page, container, false);
        vPager = (ViewPager) root.findViewById(R.id.vPager);
        tvTabAuditLabel = (TextView) root.findViewById(R.id.tvTabAuditLabel);
        tvTabReadLabel = (TextView) root.findViewById(R.id.tvTabReadLabel);

        tvTabAuditLabel.setOnClickListener(this);
        tvTabReadLabel.setOnClickListener(this);
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

        //����֪ͨ
        adapter = new FrgMainTab01ListAdapter(getActivity());
        setListAdapter(adapter);
        final ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));
        new MainTabMainPage_Notice(Config.getAccount(getActivity()), Config.getCacheToken(getActivity()), new MainTabMainPage_Notice.SuccessCallback() {
            @Override
            public void onSuccess(List<Official> data) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(data);
            }
        }, new MainTabMainPage_Notice.FailCallBack() {
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
        return root;
    }


    @Override
    public void onClick(View v) {
        resetTabBackground();
        switch (v.getId()) {
            case R.id.tvTabAuditLabel:
                vPager.setCurrentItem(0, false);
                tvTabAuditLabel.setBackgroundColor(Color.parseColor("#FFFFFF"));
            break;
            case R.id.tvTabReadLabel:
                vPager.setCurrentItem(1, false);
                tvTabReadLabel.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
        }
    }
    protected void resetTabBackground(){
        tvTabReadLabel.setBackgroundColor(Color.parseColor("#F4F4F4"));
        tvTabAuditLabel.setBackgroundColor(Color.parseColor("#F4F4F4"));
    }
}
