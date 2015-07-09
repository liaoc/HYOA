package com.huayuorg.oa.hyoa.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.huayuorg.oa.hyoa.Config;
import com.huayuorg.oa.hyoa.entities.Alert;
import com.huayuorg.oa.hyoa.entities.ContactInfo;
import com.huayuorg.oa.hyoa.entities.Official;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private static String DB_PATH = "/data/data/com.huayuorg.oa.hyoa/databases/";
    private static String ASSETS_NAME = "OA.db";
    private SQLiteDatabase myDataBase = null;
    private final Context myContext;
    //第一个文件名后缀
    private static final int ASSETS_SUFFIX_BEGIN = 101;
    //最后一个文件名后缀
    private static final int ASSETS_SUFFIX_END = 103;

    public UserDB(Context context) {
        super(context, Config.DB_DATABASE_NAME, null, Config.DB_DATABASE_VERSION);
        this.myContext = context;

        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("CREATE TABLE " + Config.DB_CONTACT_TABLE_NAME + " ("
//                + Config.DB_CONTACT_COLUMN_NUMID + " TEXT PRIMARY KEY ,"
//                + Config.DB_CONTACT_COLUMN_ACCOUNT + " TEXT NOT NULL,"
//                + Config.DB_CONTACT_COLUMN_NUMNAME + " TEXT ,"
//                + Config.DB_CONTACT_COLUMN_SEX + " INTEGER ,"
//                + Config.DB_CONTACT_COLUMN_MOBILE + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_SHOWNAME + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_CORPID + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_ORGID + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_POSTID + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_NUMSTATE + " INTEGER,"
//                + Config.DB_CONTACT_COLUMN_CORPORDER + " INTEGER,"
//                + Config.DB_CONTACT_COLUMN_ORGANORDER + " INTEGER,"
//                + Config.DB_CONTACT_COLUMN_POSTORDER + " INTEGER,"
//                + Config.DB_CONTACT_COLUMN_BIRTHDAY + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_PHOTOS + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_GROUPCORNET + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_OFFICETEL + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_ISMAINPOST + " INTEGER,"
//                + Config.DB_CONTACT_COLUMN_ENTRYDATE + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_ISADMIN + " INTEGER,"
//                + Config.DB_CONTACT_COLUMN_IDNUMER + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_EMAIL + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_CORPNAME + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_ORGNAME + " TEXT,"
//                + Config.DB_CONTACT_COLUMN_POSTNAME + " TEXT"
//                + ")");
//        db.execSQL("CREATE TABLE " + Config.DB_OFFICIAL_TABLE_NAME + " ("
//                + Config.DB_OFFICIAL_COLUMN_BILLNUM + " TEXT PRIMARY KEY ,"
//                + Config.DB_OFFICIAL_COLUMN_TITLE + " TEXT ,"
//                + Config.DB_OFFICIAL_COLUMN_TIME + " TEXT,"
//                + Config.DB_OFFICIAL_COLUMN_LINK + " TEXT,"
//                +Config.DB_OFFICIAL_COLUMN_TYPE+" TEXT"
//                + ")");
//
//        db.execSQL("CREATE TABLE " + Config.DB_ALERT_TABLE_NAME + " ("
//                        + Config.DB_ALERT_COLUMN_SEQID + " INTEGER PRIMARY KEY ,"
//                        + Config.DB_ALERT_COLUMN_BILLNUM + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_NUMID + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_ALERTTYPE + " INTEGER ,"
//                        + Config.DB_ALERT_COLUMN_TITLE + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_LOOKPATH + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_STATE + " INTEGER ,"
//                        + Config.DB_ALERT_COLUMN_DEALTIME + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_SYSCODE + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_MAKETIME + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_NUMNAME + " TEXT ,"
//                        + Config.DB_ALERT_COLUMN_BILLSTATE + " INTEGER ,"
//                        + Config.DB_ALERT_COLUMN_ALERTTYPENAME + " TEXT ) "
//        );

    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
//不做任何操作
        } else {
            //创建数据库
            try {
                File dir = new File(DB_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File dbf = new File(DB_PATH + Config.DB_DATABASE_NAME);
                if (dbf.exists()) {
                    dbf.delete();
                }
                SQLiteDatabase.openOrCreateDatabase(dbf, null);
                // 复制asseets中的数据库文件到DB_PATH下
                copyDataBase();
            } catch (IOException e) {
                throw new Error("数据库创建失败");
            }
        }
    }

    //检查数据库是否有效
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        String myPath = DB_PATH + Config.DB_DATABASE_NAME;
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
         return checkDB != null ? true : false;
    }

    /**
     * 复制assets文件中的数据库到指定路径
     * 使用输入输出流进行复制
     */
    private void copyDataBase() throws IOException {

        InputStream myInput = myContext.getAssets().open(ASSETS_NAME);
        String outFileName = DB_PATH + Config.DB_DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //复制assets下的大数据库文件时用这个
    private void copyBigDataBase() throws IOException {
        InputStream myInput;
        String outFileName = DB_PATH + Config.DB_DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        for (int i = ASSETS_SUFFIX_BEGIN; i < ASSETS_SUFFIX_END + 1; i++) {
            myInput = myContext.getAssets().open(ASSETS_NAME + "." + i);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
        }
        myOutput.close();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean SaveContactInfo(List<ContactInfo> data) {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + Config.DB_CONTACT_TABLE_NAME);

        ContentValues cv;
        ContactInfo contactInfo;
        for (int i = 0; i < data.size(); i++) {
            contactInfo = data.get(i);
            cv = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strBirthday = "", strEntryDate = "";
            strBirthday = sdf.format(contactInfo.getBirthday());
            strEntryDate = sdf.format(contactInfo.getEntrydate());
            //  System.out.println(strBirthday+" "+strEntryDate);
            cv.put(Config.DB_CONTACT_COLUMN_NUMID, contactInfo.getNumid());
            cv.put(Config.DB_CONTACT_COLUMN_ACCOUNT, contactInfo.getAccount());
            cv.put(Config.DB_CONTACT_COLUMN_NUMNAME, contactInfo.getNumname());
            cv.put(Config.DB_CONTACT_COLUMN_SEX, contactInfo.getSex());
            cv.put(Config.DB_CONTACT_COLUMN_MOBILE, contactInfo.getMobile());
            cv.put(Config.DB_CONTACT_COLUMN_SHOWNAME, contactInfo.getShowname());
            cv.put(Config.DB_CONTACT_COLUMN_CORPID, contactInfo.getCorpid());
            cv.put(Config.DB_CONTACT_COLUMN_ORGID, contactInfo.getOrgid());
            cv.put(Config.DB_CONTACT_COLUMN_POSTID, contactInfo.getPostid());
            cv.put(Config.DB_CONTACT_COLUMN_NUMSTATE, contactInfo.getNumstate());
            cv.put(Config.DB_CONTACT_COLUMN_CORPORDER, contactInfo.getCorporder());
            cv.put(Config.DB_CONTACT_COLUMN_ORGANORDER, contactInfo.getOrganorder());
            cv.put(Config.DB_CONTACT_COLUMN_POSTORDER, contactInfo.getPostorder());
            cv.put(Config.DB_CONTACT_COLUMN_BIRTHDAY, strBirthday);
            cv.put(Config.DB_CONTACT_COLUMN_PHOTOS, contactInfo.getPhotos());
            cv.put(Config.DB_CONTACT_COLUMN_GROUPCORNET, contactInfo.getGroupcornet());
            cv.put(Config.DB_CONTACT_COLUMN_OFFICETEL, contactInfo.getOfficetel());
            cv.put(Config.DB_CONTACT_COLUMN_ISMAINPOST, contactInfo.getIsmainpost());
            cv.put(Config.DB_CONTACT_COLUMN_ENTRYDATE, strEntryDate);
            cv.put(Config.DB_CONTACT_COLUMN_ISADMIN, contactInfo.getIsadmin());
            cv.put(Config.DB_CONTACT_COLUMN_IDNUMER, contactInfo.getIdnumber());
            cv.put(Config.DB_CONTACT_COLUMN_EMAIL, contactInfo.getEmail());
            cv.put(Config.DB_CONTACT_COLUMN_CORPNAME, contactInfo.getCorpname());
            cv.put(Config.DB_CONTACT_COLUMN_ORGNAME, contactInfo.getOrgname());
            cv.put(Config.DB_CONTACT_COLUMN_POSTNAME, contactInfo.getPostname());
            sqLiteDatabase.insert(Config.DB_CONTACT_TABLE_NAME, null, cv);
        }
        return true;
    }

    public List<ContactInfo> getListContactInfo(Cursor cursor) {
        List<ContactInfo> data = new ArrayList<ContactInfo>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Date dateBirthday = new Date(), dateEntryDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        sqLiteDatabase = getWritableDatabase();
      //  sqLiteDatabase.execSQL("delete from " + Config.DB_OFFICIAL_TABLE_NAME);

        ContentValues cv;
        Official official;
        for (int i = 0; i < data.size(); i++) {
            official = data.get(i);
            cv = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strTime = "";
            strTime = sdf.format(official.getTime());
            cv.put(Config.DB_OFFICIAL_COLUMN_TITLE, official.getTitle());
            cv.put(Config.DB_OFFICIAL_COLUMN_BILLNUM, official.getBillnum());
            cv.put(Config.DB_OFFICIAL_COLUMN_TIME, strTime);
            cv.put(Config.DB_OFFICIAL_COLUMN_LINK, official.getLink());
            cv.put(Config.DB_OFFICIAL_COLUMN_TYPE,official.getType());
            cv.put(Config.DB_OFFICIAL_COLUMN_NUMID,official.getNumid());
            sqLiteDatabase.insert(Config.DB_OFFICIAL_TABLE_NAME, null, cv);
        }
        return true;
    }

    public List<Official> getListOfficialInfo(Cursor cursor) {
        List<Official> data = new ArrayList<Official>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Date dateTime = new Date(), dateEntryDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                dateTime = sdf.parse(cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_TIME)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            data.add(new Official(cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_BILLNUM)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_LINK)),
                    dateTime,

                    cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_TYPE)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_OFFICIAL_COLUMN_NUMID))));
        }
        return data;
    }

    public boolean SaveAlertInfo(List<Alert> data, String type) {
        sqLiteDatabase = getWritableDatabase();
//        if (type == Config.KEY_ALERT_READ) {
//            sqLiteDatabase.execSQL("delete from " + Config.DB_ALERT_TABLE_NAME + " where " + Config.DB_ALERT_COLUMN_ALERTTYPE + " in (3,4,5,9,10) ");
//        } else if (type == Config.KEY_ALERT_AUDIT) {
//            sqLiteDatabase.execSQL("delete from " + Config.DB_ALERT_TABLE_NAME + " where " + Config.DB_ALERT_COLUMN_ALERTTYPE + " in (1,2,6,7,8,63,64) ");
//        }

        ContentValues cv;
        Alert alert;
        for (int i = 0; i < data.size(); i++) {
            alert = data.get(i);
            cv = new ContentValues();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDealTime = "",strMakeTime="";
            strDealTime = sdf.format(alert.getDealtime());
            strMakeTime=sdf.format(alert.getMaketime());
            cv.put(Config.DB_ALERT_COLUMN_SEQID, alert.getSeqid());
            cv.put(Config.DB_ALERT_COLUMN_BILLNUM, alert.getBillnum());
            cv.put(Config.DB_ALERT_COLUMN_NUMID, alert.getNumid());
            cv.put(Config.DB_ALERT_COLUMN_ALERTTYPE, alert.getAlerttype());
            cv.put(Config.DB_ALERT_COLUMN_LOOKPATH, alert.getLookpath());
            cv.put(Config.DB_ALERT_COLUMN_STATE, alert.getState());
            cv.put(Config.DB_ALERT_COLUMN_SYSCODE, alert.getSyscode());
            cv.put(Config.DB_ALERT_COLUMN_TITLE, alert.getTitle());
            cv.put(Config.DB_ALERT_COLUMN_DEALTIME, strDealTime);
            cv.put(Config.DB_ALERT_COLUMN_MAKETIME,strMakeTime);
            cv.put(Config.DB_ALERT_COLUMN_NUMNAME,alert.getNumname());
            cv.put(Config.DB_ALERT_COLUMN_BILLSTATE,alert.getBillstate());
            cv.put(Config.DB_ALERT_COLUMN_ALERTTYPENAME,alert.getAlerttypename());
            sqLiteDatabase.insert(Config.DB_ALERT_TABLE_NAME, null, cv);
        }
        return true;

    }

    public List<Alert> getListAlertInfo(Cursor cursor) {
        List<Alert> data = new ArrayList<Alert>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Date dealTime = new Date(), makeTime = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                makeTime = sdf.parse(cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_MAKETIME)));

                dealTime = sdf.parse(cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_DEALTIME)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            data.add(new Alert(cursor.getInt(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_SEQID)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_BILLNUM)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_NUMID)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_ALERTTYPE)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_LOOKPATH)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_STATE)),
                    dealTime,
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_SYSCODE)),
                    makeTime,
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_NUMNAME)),
                    cursor.getInt(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_BILLSTATE)),
                    cursor.getString(cursor.getColumnIndex(Config.DB_ALERT_COLUMN_ALERTTYPENAME))
            ));
        }
        return data;
    }
}
