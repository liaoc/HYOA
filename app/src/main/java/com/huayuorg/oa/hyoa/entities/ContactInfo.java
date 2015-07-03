package com.huayuorg.oa.hyoa.entities;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/30.
 */
public class ContactInfo {
    private String numid;
    private String account;
    private String numname;
    private String mobile;
    private int sex;
    private String showname;
    private String corpid;
    private String orgid;
    private String postid;
    private int numstate;
    private int corporder;
    private int organorder;
    private int postorder;
    private Date birthday;
    private String photos;
    private String groupcornet;
    private String officetel;
    private int ismainpost;
    private Date entrydate;
    private int isadmin;
    private String idnumber;
    private String email;
    private String corpname;
    private String orgname;
    private String postname;

    public ContactInfo(String numid, String account, String numname, String mobile,
                       int sex, String showname, String corpid, String orgid, String postid,
                       int numstate, int corporder, int organorder, int postorder, Date birthday,
                       String photos, String groupcornet, String officetel, int ismainpost,
                       Date entrydate, int isadmin, String idnumber, String email, String corpname, String orgname, String postname) {
        this.numid = numid;
        this.account = account;
        this.numname = numname;
        this.mobile = mobile;
        this.sex = sex;
        this.showname = showname;
        this.corpid = corpid;
        this.orgid = orgid;
        this.postid = postid;
        this.numstate = numstate;
        this.corporder = corporder;
        this.organorder = organorder;
        this.postorder = postorder;
        this.birthday = birthday;
        this.photos = photos;
        this.groupcornet = groupcornet;
        this.officetel = officetel;
        this.ismainpost = ismainpost;
        this.entrydate = entrydate;
        this.isadmin = isadmin;
        this.idnumber = idnumber;
        this.email = email;
        this.corpname = corpname;
        this.orgname = orgname;
        this.postname = postname;
    }

    public String getNumid() {
        return numid;
    }

    public String getAccount() {
        return account;
    }

    public String getNumname() {
        return numname;
    }

    public String getMobile() {
        return mobile;
    }

    public int getSex() {
        return sex;
    }

    public String getShowname() {
        return showname;
    }

    public String getCorpid() {
        return corpid;
    }

    public String getOrgid() {
        return orgid;
    }

    public String getPostid() {
        return postid;
    }

    public int getNumstate() {
        return numstate;
    }

    public int getCorporder() {
        return corporder;
    }

    public int getOrganorder() {
        return organorder;
    }

    public int getPostorder() {
        return postorder;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPhotos() {
        return photos;
    }

    public String getGroupcornet() {
        return groupcornet;
    }

    public String getOfficetel() {
        return officetel;
    }

    public int getIsmainpost() {
        return ismainpost;
    }

    public Date getEntrydate() {
        return entrydate;
    }

    public int getIsadmin() {
        return isadmin;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCorpname() {
        return corpname;
    }

    public String getOrgname() {
        return orgname;
    }

    public String getPostname() {
        return postname;
    }
}
