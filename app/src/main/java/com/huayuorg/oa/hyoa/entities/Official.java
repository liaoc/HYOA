package com.huayuorg.oa.hyoa.entities;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/26.
 */
public class Official {
    private String billnum = null, title = null, link = null;
    private Date time = new Date();
    private String type,numid;




    public Official(String billnum, String title, String link, Date time, String type, String numid) {
        this.billnum = billnum;
        this.title = title;
        this.link = link;
        this.time = time;
        this.type = type;
        this.numid = numid;
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

    public String getType() {
        return type;
    }

    public String getNumid() {
        return numid;
    }
}
