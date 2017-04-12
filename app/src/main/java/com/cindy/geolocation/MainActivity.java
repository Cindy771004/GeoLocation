package com.cindy.geolocation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";

    private static final int PERMISSION_CONTACTS = 100;
    private final Activity thisActivity = this;
    private CoordinatorLayout mCoordinatorLayout;//Snackbar所使用的容器
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext= getApplicationContext();
        mCoordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        checkPermission();
        getGeoLocation();
        getTimeZone();
    }

    private String getGeoLocation(){
        Log.d(TAG, "getGeoLocation ");
        GeoLocation gps = new GeoLocation(mContext);
        gps.getLocation();
        if(gps.canGetLocation()){
            String latitude = gps.getLatitude();
            String longitude = gps.getLongitude();
            Log.d(TAG, "Location: "+latitude+";"+longitude);
            gps.stopUsingGPS();
            return latitude+";"+longitude;
        }else{
            Log.e(TAG,"GeoLocation Error");
            return "GeoLocation Error";
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private int getTimeZone(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        String timezone = new SimpleDateFormat("Z").format(currentLocalTime);
        Log.d(TAG, "getTimeZone: "+Integer.valueOf(timezone)/100*60);
        return Integer.valueOf(timezone)/100*60;
    }

    public void checkPermission(){
        int locationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //如果LOCATION的權限未授權，則去要求權限
        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission was granted ");
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        Log.d(TAG, "Permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions, PERMISSION_CONTACTS);
            }
        };

        Snackbar.make(mCoordinatorLayout,
                R.string.permission_rationale,//顯示的文字
                Snackbar.LENGTH_INDEFINITE)  //顯示時間
                .setAction(R.string.ok, listener) //如果點了OK，則呼叫listener
                .show();
    }
}
