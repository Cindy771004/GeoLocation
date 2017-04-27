package com.cindy.geolocation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.TabHost;

/**
 * Created by Cindyyh_Chou on 2017/4/14.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static String TAG="DBHelper";

    // 資料庫名稱
    public static final String DATABASE_NAME = "mydata.db";
    // 資料庫版本
    public static final int VERSION = 2;
    // 資料庫物件
    private static SQLiteDatabase database;

    // 建構子
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // 取得資料庫的元件
    public static SQLiteDatabase getDatabase(Context context) {
        Log.d(TAG, "getDatabase ");

        if (database == null || !database.isOpen()) {
            database = new DBHelper(context).getWritableDatabase();
        }
        return database;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate");
        // Android 載入時找不到生成的資料庫檔案，就會觸發 onCreate

        // 建立應用程式需要的表格
        // execSQL:直接執行SQL語句
        sqLiteDatabase.execSQL(ItemDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        // 如果資料庫結構有改變了就會觸發 onUpgrade
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        // 刪除原有的表格
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_NAME);

        // 呼叫onCreate建立新版的表格
        onCreate(sqLiteDatabase);
    }
}
