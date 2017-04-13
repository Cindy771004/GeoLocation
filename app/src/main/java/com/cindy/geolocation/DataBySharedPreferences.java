package com.cindy.geolocation;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Cindyyh_Chou on 2017/4/13.
 */

public class DataBySharedPreferences {
    private String TAG="DataBySharedPreferences";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;
    private String SharedPreferencesDataName="Cindy";
    private Context mContext;
    DataBySharedPreferences(Context mContext){
        this.mContext= mContext;
    }
    public void saveData(){
        //使用getSharedPreferences方法取得SharedPreferences物件
        //丟入的參數為("名稱" , 存取權限)
        //存取權限:MODE_PRIVATE，代表新值會覆蓋舊值
        //xml檔案存放路徑/data/data/YOUR_PACKAGE_NAME/shared_prefs/YOUR_PREFS_NAME.xml
        mSharedPreferences= mContext.getSharedPreferences(SharedPreferencesDataName,mContext.MODE_PRIVATE);

        //取得SharedPreferences.Editor物件
        mSharedPreferencesEditor= mSharedPreferences.edit();

        //存放data,("KEY","VALUE")
        mSharedPreferencesEditor.putString("Name","Cindy");
        mSharedPreferencesEditor.putString("Age","18");

        //commit
        mSharedPreferencesEditor.commit();
    }
    public void readData(){
        //取得SharedPreferences物件
        mSharedPreferences= mContext.getSharedPreferences(SharedPreferencesDataName,mContext.MODE_PRIVATE);

        //透過get取得data, ("KEY",”默認值”)
        String name= mSharedPreferences.getString("Name","null");
        String age= mSharedPreferences.getString("Age","null");

        Log.d(TAG, "Data= name:"+name+",age:"+age);
    }
}
