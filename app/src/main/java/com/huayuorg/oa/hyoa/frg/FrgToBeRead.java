package com.huayuorg.oa.hyoa.frg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huayuorg.oa.hyoa.R;

/**
 * Created by Administrator on 2015/6/23.
 */
public class FrgToBeRead extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_to_be_read,container,false);
    }
}
