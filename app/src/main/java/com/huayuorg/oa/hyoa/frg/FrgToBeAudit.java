package com.huayuorg.oa.hyoa.frg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.adapter.FrgMainTab01ListAdapter;
import com.huayuorg.oa.hyoa.atys.AtyLogin;
import com.huayuorg.oa.hyoa.net.MainTabMainPage_Audit;
import com.huayuorg.oa.hyoa.entities.Official;

import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FrgToBeAudit extends ListFragment {
    FrgMainTab01ListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
View root=inflater.inflate(R.layout.frg_to_be_audit,container,false);
        adapter=new FrgMainTab01ListAdapter(getActivity());
        setListAdapter(adapter);
        new MainTabMainPage_Audit(Config.getAccount(getActivity()), Config.getCacheToken(getActivity()), new MainTabMainPage_Audit.SuccessCallback() {
            @Override
            public void onSuccess(List<Official> data) {
adapter.clear();
                adapter.addAll(data);
            }
        }, new MainTabMainPage_Audit.FailCallback() {
            @Override
            public void onFail(int errCode) {
                if(errCode==Config.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(getActivity(), AtyLogin.class));
                }
                else{
                    Toast.makeText(getActivity(),R.string.connecting,Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }
}
