package com.huayuorg.oa.hyoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huayuorg.oa.hyoa.R;
import com.huayuorg.oa.hyoa.entities.Alert;
import com.huayuorg.oa.hyoa.entities.Official;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/26.
 */
public class NoticeAdapter extends BaseAdapter {
    List<Official> data = new ArrayList<Official>();
    private Context context = null;

    public NoticeAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void addAll(List<Official> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.aty_main_tabs_mainpage_listcell, null);
            convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.tvTitle),
                    (TextView) convertView.findViewById(R.id.tvName),
                    (TextView) convertView.findViewById(R.id.tvTime),
                    (TextView) convertView.findViewById(R.id.tvDeal)));
        }
        ListCell lc = (ListCell) convertView.getTag();
        Official official = data.get(position);
        String tempTitle = official.getTitle();
        lc.getTvTitle().setText(tempTitle);
        lc.getTvTime().setText(new SimpleDateFormat("yyyy-MM-dd").format(official.getTime()));
        lc.getTvName().setText(official.getType());

        lc.getTvDeal().setText("未阅");

        return convertView;
    }

    public static class ListCell {
        private TextView tvTitle;
        private TextView tvName;
        private TextView tvTime;

        public TextView getTvDeal() {
            return tvDeal;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvTime() {
            return tvTime;
        }

        private TextView tvDeal;

        public ListCell(TextView tvTitle, TextView tvName, TextView tvTime, TextView tvDeal) {
            this.tvTitle = tvTitle;
            this.tvName = tvName;
            this.tvTime = tvTime;
            this.tvDeal = tvDeal;
        }
    }
}
