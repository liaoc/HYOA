package com.huayuorg.oa.hyoa.entities;


import java.util.Date;

/**
 * Created by Administrator on 2015/7/6.
 */
public class Alert {
    private  int seqid;
    private String billnum;
    private String numid;
    private int alerttype;
    private String title;
    private String lookpath;
    private int state;
    private Date dealtime;
    private String syscode;

    private Date maketime;
    private String numname;
    private int billstate;
    private String alerttypename;

    public Alert(int seqid, String billnum, String numid, int alerttype, String title, String lookpath, int state, Date dealtime, String syscode, Date maketime, String numname, int billstate, String alerttypename) {
        this.seqid = seqid;
        this.billnum = billnum;
        this.numid = numid;
        this.alerttype = alerttype;
        this.title = title;
        this.lookpath = lookpath;
        this.state = state;
        this.dealtime = dealtime;
        this.syscode = syscode;
        this.maketime = maketime;
        this.numname = numname;
        this.billstate = billstate;
        this.alerttypename = alerttypename;
    }

    public int getSeqid() {
        return seqid;
    }

    public String getBillnum() {
        return billnum;
    }

    public String getNumid() {
        return numid;
    }

    public int getAlerttype() {
        return alerttype;
    }

    public String getTitle() {
        return title;
    }

    public String getLookpath() {
        return lookpath;
    }

    public int getState() {
        return state;
    }

    public Date getDealtime() {
        return dealtime;
    }

    public String getSyscode() {
        return syscode;
    }

    public Date getMaketime() {
        return maketime;
    }

    public String getNumname() {
        return numname;
    }

    public int getBillstate() {
        return billstate;
    }

    public String getAlerttypename() {
        return alerttypename;
    }

}
