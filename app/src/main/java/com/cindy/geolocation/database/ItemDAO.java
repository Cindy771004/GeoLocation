package com.cindy.geolocation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindyyh_Chou on 2017/4/14.
 */

public class ItemDAO {
    private String TAG="ItemDAO";
    // 表格名稱
    public static final String TABLE_NAME = "GeoLocation";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String LATITUDE_COLUMN = "latitude";
    public static final String LONGITUDE_COLUMN = "longitude";

    // 使用上面宣告的變數建立表格的SQL指令
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS" +TABLE_NAME+ "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LATITUDE_COLUMN + " REAL, " +
                    LONGITUDE_COLUMN + " REAL) ";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子
    public ItemDAO(Context context) {
        db = DBHelper.getDatabase(context);
    }

    // 關閉資料庫
    public void close() {
        db.close();
    }

    // 新增DB資料
    public void insert(String lat,String lon) {
        Log.d(TAG, "insert");

        // 使用ContentValues物件去新增資料
        ContentValues cv = new ContentValues();

        // 放入data至ContentValues中
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        cv.put(LATITUDE_COLUMN, lat);
        cv.put(LONGITUDE_COLUMN, lon);

        // 新增一筆資料
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        db.insert(TABLE_NAME, null, cv);

    }

    public void delete(String id){
        Log.d(TAG, "delete");

        String deleteSQL = "_id" + " = " + id ;
        db.execSQL(deleteSQL);
    }

    public void query(){
        Log.d(TAG, "query");

        Cursor cursor= db.rawQuery("Select * from "+TABLE_NAME,null);
        if(cursor.moveToFirst() == false){
            return ;
        }
        do{
            String lat= cursor.getString(cursor.getColumnIndex(LATITUDE_COLUMN));
            String lon= cursor.getString(cursor.getColumnIndex(LONGITUDE_COLUMN));

            Log.d(TAG, "lat: "+lat+",lon: "+lon );
        }while(cursor.moveToNext());
    }

}
