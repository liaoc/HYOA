package com.huayuorg.oa.hyoa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.entities.ContactInfo;
import com.huayuorg.oa.hyoa.entities.Official;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/30.
 */
public class UserDB extends SQLiteOpenHelper {

    public SQLiteDatabase sqLiteDatabase;

    public UserDB(Context context) {
        super(context, Config.DB_DATABASE_NAME, null, Config.DB_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Config.DB_CONTACT_TABLE_NAME + " ("
                + Config.DB_CONTACT_COLUMN_NUMID + " TEXT PRIMARY KEY ,"
                + Config.DB_CONTACT_COLUMN_ACCOUNT + " TEXT NOT NULL,"
                + Config.DB_CONTACT_COLUMN_NUMNAME + " TEXT ,"
                + Config.DB_CONTACT_COLUMN_SEX + " INTEGER ,"
                + Config.DB_CONTACT_COLUMN_MOBILE + " TEXT,"
                + Config.DB_CONTACT_COLUMN_SHOWNAME + " TEXT,"
                + Config.DB_CONTACT_COLUMN_CORPID + " TEXT,"
                + Config.DB_CONTACT_COLUMN_ORGID + " TEXT,"
                + Config.DB_CONTACT_COLUMN_POSTID + " TEXT,"
                + Config.DB_CONTACT_COLUMN_NUMSTATE + " INTEGER,"
                + Config.DB_CONTACT_COLUMN_CORPORDER + " INTEGER,"
                + Config.DB_CONTACT_COLUMN_ORGANORDER + " INTEGER,"
                + Config.DB_CONTACT_COLUMN_POSTORDER + " INTEGER,"
                + Config.DB_CONTACT_COLUMN_BIRTHDAY + " TEXT,"
                + Config.DB_CONTACT_COLUMN_PHOTOS + " TEXT,"
                + Config.DB_CONTACT_COLUMN_GROUPCORNET + " TEXT,"
                + Config.DB_CONTACT_COLUMN_OFFICETEL + " TEXT,"
                + Config.DB_CONTACT_COLUMN_ISMAINPOST + " INTEGER,"
                + Config.DB_CONTACT_COLUMN_ENTRYDATE + " TEXT,"
                + Config.DB_CONTACT_COLUMN_ISADMIN + " INTEGER,"
                + Config.DB_CONTACT_COLUMN_IDNUMER + " TEXT,"
                + Config.DB_CONTACT_COLUMN_EMAIL + " TEXT,"
                + Config.DB_CONTACT_COLUMN_CORPNAME + " TEXT,"
                + Config.DB_CONTACT_COLUMN_ORGNAME + " TEXT,"
                + Config.DB_CONTACT_COLUMN_POSTNAME + " TEXT"
                + ")");
        db.execSQL("CREATE TABLE " + Config.DB_OFFICIAL_TABLE_NAME + " ("
                + Config.DB_OFFICIAL_COLUMN_BILLNUM + " TEXT PRIMARY KEY ,"
                + Config.DB_OFFICIAL_COLUMN_TITLE + " TEXT ,"
                + Config.DB_OFFICIAL_COLUMN_TIME + " TEXT,"
                + Config.DB_OFFICIAL_COLUMN_LINK + " TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean SaveContactInfo(List<ContactInfo> data) {
        sqLiteDatabase = getWritableDatabase();
     sqLiteDatabase.execSQL("delete from "+Config.DB_CONTACT_TABLE_NAME);

        ContentValues cv;
        ContactInfo contactInfo;
        for (int i = 0; i < data.size(); i++) {
            contactInfo = data.get(i);
            cv = new ContentValues();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String strBirthday="",strEntryDate="";
            strBirthday=sdf.format(contactInfo.getBirthday());
            strEntryDate=sdf.format(contactInfo.getEntrydate());
          //  System.out.println(strBirthday+" "+strEntryDate);
            cv.put(Config.DB_CONTACT_COLUMN_NUMID, contactInfo.getNumid());
            cv.put(Config.DB_CONTACT_COLUMN_ACCOUNT,contactInfo.getAccount());
            cv.put(Config.DB_CONTACT_COLUMN_NUMNAME,contactInfo.getNumname());
            cv.put(Config.DB_CONTACT_COLUMN_SEX,contactInfo.getSex());
            cv.put(Config.DB_CONTACT_COLUMN_MOBILE,contactInfo.getMobile());
            cv.put(Config.DB_CONTACT_COLUMN_SHOWNAME,contactInfo.getShowname());
            cv.put(Config.DB_CONTACT_COLUMN_CORPID,contactInfo.getCorpid());
            cv.put(Config.DB_CONTACT_COLUMN_ORGID,contactInfo.getOrgid());
            cv.put(Config.DB_CONTACT_COLUMN_POSTID,contactInfo.getPostid());
            cv.put(Config.DB_CONTACT_COLUMN_NUMSTATE,contactInfo.getNumstate());
            cv.put(Config.DB_CONTACT_COLUMN_CORPORDER,contactInfo.getCorporder());
            cv.put(Config.DB_CONTACT_COLUMN_ORGANORDER,contactInfo.getOrganorder());
            cv.put(Config.DB_CONTACT_COLUMN_POSTORDER,contactInfo.getPostorder());
            cv.put(Config.DB_CONTACT_COLUMN_BIRTHDAY,strBirthday);
            cv.put(Config.DB_CONTACT_COLUMN_PHOTOS,contactInfo.getPhotos());
            cv.put(Config.DB_CONTACT_COLUMN_GROUPCORNET,contactInfo.getGroupcornet());
            cv.put(Config.DB_CONTACT_COLUMN_OFFICETEL,contactInfo.getOfficetel());
            cv.put(Config.DB_CONTACT_COLUMN_ISMAINPOST,contactInfo.getIsmainpost());
            cv.put(Config.DB_CONTACT_COLUMN_ENTRYDATE,strEntryDate);
            cv.put(Config.DB_CONTACT_COLUMN_ISADMIN,contactInfo.getIsadmin());
            cv.put(Config.DB_CONTACT_COLUMN_IDNUMER,contactInfo.getIdnumber());
            cv.put(Config.DB_CONTACT_COLUMN_EMAIL,contactInfo.getEmail());
            cv.put(Config.DB_CONTACT_COLUMN_CORPNAME,contactInfo.getCorpname());
            cv.put(Config.DB_CONTACT_COLUMN_ORGNAME,contactInfo.getOrgname());
            cv.put(Config.DB_CONTACT_COLUMN_POSTNAME,contactInfo.getPostname());
            sqLiteDatabase.insert(Config.DB_CONTACT_TABLE_NAME, null, cv);
        }
        return true;
    }
    public List<ContactInfo> getListContactInfo(Cursor cursor) {
        List<ContactInfo> data = new ArrayList<ContactInfo>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Date dateBirthday = new Date(), dateEntryDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateBirthday = sdf.parse(cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_BIRTHDAY)));
                dateEntryDate = sdf.parse(cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ENTRYDATE)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
          //  System.out.println(cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_BIRTHDAY)));
            data.add(new ContactInfo(cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_NUMID)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ACCOUNT)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_NUMNAME)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_MOBILE)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_SEX)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_SHOWNAME)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_CORPID)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ORGID)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_POSTID)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_NUMSTATE)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_CORPORDER)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ORGANORDER)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_POSTORDER)),
                    dateBirthday,
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_PHOTOS)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_GROUPCORNET)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_OFFICETEL)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ISMAINPOST)),
                    dateEntryDate,
                    cursor.getInt(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ISADMIN)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_IDNUMER)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_CORPNAME)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_ORGNAME)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_CONTACT_COLUMN_POSTNAME))
            ));
        }
        return data;
    }
    public boolean SaveOfficialInfo(List<Official> data) {
        sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+Config.DB_OFFICIAL_TABLE_NAME);

        ContentValues cv;
        Official official;
        for (int i = 0; i < data.size(); i++) {
            official = data.get(i);
            cv = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strTime = "";
            strTime = sdf.format(official.getTime());
           cv.put(Config.DB_OFFICIAL_COLUMN_TITLE,official.getTitle());
            cv.put(Config.DB_OFFICIAL_COLUMN_BILLNUM,official.getBillnum());
            cv.put(Config.DB_OFFICIAL_COLUMN_TIME,strTime);
            cv.put(Config.DB_OFFICIAL_COLUMN_LINK,official.getLink());
            sqLiteDatabase.insert(Config.DB_OFFICIAL_TABLE_NAME, null, cv);
        }
        return true;
    }
    public List<Official> getListOfficialInfo(Cursor cursor){
        List<Official> data=new ArrayList<Official>();
        for(int i=0;i<cursor.getCount();i++) {
            cursor.moveToPosition(i);
            Date dateTime = new Date(), dateEntryDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dateTime = sdf.parse(cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_TIME)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            data.add(new Official(cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_BILLNUM)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_TITLE)),
                    dateTime,
                    cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_BILLNUM))));
        }
        return data;
    }
}
