package com.huayuorg.oa.hyoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.entities.ContactInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/30.
 */
public class AtyContactListAdapter extends BaseAdapter {
    List<ContactInfo> data = new ArrayList<ContactInfo>();
    private Context context;

    public AtyContactListAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ContactInfo> contactInfos) {

        data.addAll(contactInfos);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.frg_main_tab_contact_listcell, null);
            convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.tvContactShowName), (TextView) convertView.findViewById(R.id.tvContactNumName),
                    (ImageView) convertView.findViewById(R.id.ivContactPhoto)));
        }
        ListCell lc = (ListCell) convertView.getTag();
        ContactInfo contactInfo = data.get(position);
        if (contactInfo.getSex() == 0) {
            lc.getIvPhoto().setImageResource(R.drawable.contact_photo_male);
        } else {
            lc.getIvPhoto().setImageResource(R.drawable.contact_photo_female);

        }
        String numConnectString=contactInfo.getOfficetel().length()>0?"座机：" + contactInfo.getOfficetel():"";
        numConnectString+=" 手机：" + contactInfo.getMobile();
        numConnectString+=contactInfo.getGroupcornet().length()>0?" 短号："+contactInfo.getGroupcornet():"";
        lc.getTvNumName().setText(contactInfo.getNumname() + " (" + contactInfo.getOrgname() + "-" + contactInfo.getPostname() + ")" );
        lc.getTvShowName().setText(numConnectString);
        return convertView;
    }

    public class ListCell {
        private TextView tvShowName;
        private TextView tvNumName;
        private ImageView ivPhoto;

        public ListCell(TextView tvShowName, TextView tvNumName, ImageView ivPhoto) {
            this.tvShowName = tvShowName;
            this.tvNumName = tvNumName;
            this.ivPhoto = ivPhoto;
        }

        public TextView getTvShowName() {
            return tvShowName;
        }

        public TextView getTvNumName() {
            return tvNumName;
        }

        public ImageView getIvPhoto() {
            return ivPhoto;
        }
    }
}
