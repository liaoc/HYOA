package com.huayuorg.oa.hyoa.entities;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/26.
 */
public class Official {
    private String billnum = null, title = null, link = null;
    private Date time = new Date();

    public Official(String billnum, String title, Date time, String link) {
        this.billnum = billnum;
        this.title = title;
        this.time = time;
        this.link = link;
    }

    public String getBillnum() {
        return billnum;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Date getTime() {
        return time;
    }
}
