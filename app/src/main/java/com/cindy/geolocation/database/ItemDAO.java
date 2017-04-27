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
    public static final String TABLE_NAME = "UserData";

    // 編號表格欄位名稱，固定不變
    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String NAME_COLUMN = "name";
    public static final String AGE_COLUMN = "age";
    private String[] ALLCOLUMNS = { KEY_ID, NAME_COLUMN, AGE_COLUMN };

    // 使用上面宣告的變數建立表格的SQL指令
    // SQLite 的 Type 只有 TEXT, INTEGER, REAL ( similar to double in java) 三種
    public static final String CREATE_TABLE =
            " CREATE TABLE IF NOT EXISTS " +TABLE_NAME+ "(" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN + " TEXT, " +
                    AGE_COLUMN + " INTEGER ) ";

    // 資料庫物件
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // 建構子
    public ItemDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    //取得DB物件
    public void open(){
        Log.d(TAG, "open DB");

        db = dbHelper.getWritableDatabase();
    }

    // 關閉資料庫
    public void close() {
        Log.d(TAG, "close DB ");

        db.close();
    }

//    // 新增DB資料
//    public void insert(String name,int age) {
//        Log.d(TAG, "insert (name,age)=("+name+","+age+")");
//
//        // 使用ContentValues物件去新增資料
//        ContentValues cv = new ContentValues();
//        // 放入data至ContentValues中
//        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
//        cv.put(NAME_COLUMN, name);
//        cv.put(AGE_COLUMN, age);
//
//        // 新增一筆資料
//        // 第一個參數是表格名稱
//        // 第二個參數是沒有指定欄位值的預設值
//        // 第三個參數是包裝新增資料的ContentValues物件
//        db.insert(TABLE_NAME, null, cv);
//
//    }
//
//    public void delete(long id){
//        Log.d(TAG, "delete");
//
//        String deleteSQL = "_id " + " = " + id ;
//        db.execSQL(deleteSQL);
//    }
//
//    public void query(){
//        Log.d(TAG, "query");
//
//        Cursor cursor= db.rawQuery("Select * from "+TABLE_NAME,null);
//        if(cursor.moveToFirst() == false){
//            return ;
//        }
//        do{
//            String name= cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
//            String age= cursor.getString(cursor.getColumnIndex(AGE_COLUMN));
//
//            Log.d(TAG, "name: "+name+",age: "+age );
//        }while(cursor.moveToNext());
//    }

    public List<Item> getAllData(){
        Log.d(TAG, "getAllData");

        List<Item> allData= new ArrayList<Item>();
        Cursor cursor = db.query(TABLE_NAME, ALLCOLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item item= new Item();
            int nameColumnIndex = cursor.getColumnIndex(NAME_COLUMN);//取得NAME_COLUMN在cursor的第幾欄
            item.setId(cursor.getLong(nameColumnIndex)); //取得第幾欄資料
//            item.setId(cursor.getLong(0));  //或是如果知道資料在第幾欄的話 可以直接取得cursor的第幾欄資料
            item.setName(cursor.getString(1)); //取得cursor的第1欄資料(LATITUDE_COLUMN)
            item.setAge(cursor.getInt(2));

            allData.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d(TAG, "AllData: "+allData);
        return allData;

    }

    public Item inserItem(String name , int age){
        Log.d(TAG, "inserItem (name,age)="+name+","+age);

        ContentValues values = new ContentValues();
        values.put(NAME_COLUMN,name);
        values.put(AGE_COLUMN,age);

        long insertId = db.insert(TABLE_NAME, null, values);

        Cursor cursor = db.query(TABLE_NAME,
                ALLCOLUMNS, KEY_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();

        Item item= cursorToItem(cursor);
        cursor.close();

        return item;
    }

    public void deleteItem(Item item){
        Log.d(TAG, "deleteItem");
        long id = item.getId();
        db.delete(TABLE_NAME, KEY_ID + " = " + id, null);
    }

    private Item cursorToItem(Cursor cursor) {
        Log.d(TAG, "cursorToItem");

        Item item= new Item();
        item.setId(cursor.getLong(0));
        item.setName(cursor.getString(1));
        item.setAge(cursor.getInt(2));

        return item;
    }


}
