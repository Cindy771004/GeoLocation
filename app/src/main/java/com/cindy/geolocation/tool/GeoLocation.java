package com.cindy.geolocation.tool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.DecimalFormat;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Cindyyh_Chou on 2017/4/10.
 */

public class GeoLocation implements LocationListener {

    private String TAG = "GeoLocation";

    private final Context mContext;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;
    private static final long MIN_UPDATE_DISTANCE = 10; // The minimum distance to change Updates in meters, 10 meters
    private static final long MIN_UPDATE_TIME = 1000 * 60; // The minimum time between updates in milliseconds, 1 minute

    public GeoLocation(Context context) {
        this.mContext = context;
    }

    public Location getLocation() {
        Log.d(TAG, "getLocation");

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.e(TAG, "GSP/Network not enable");
                return null;
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "ACCESS_FINE_LOCATION permission not granted");
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                } else if (isGPSEnabled) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "ACCESS_FINE_LOCATION permission not granted");
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_TIME, MIN_UPDATE_DISTANCE, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public String getLatitude() {
        Log.d(TAG, "getLatitude");
        if (location == null) {
            return "getLatitude error";
        }

        latitude = location.getLatitude();
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        return "lat:" + decimalFormat.format(latitude);
    }

    public String getLongitude() {
        Log.d(TAG, "getLongitude");
        if (location == null) {
            return "getLongitude error";
        }

        longitude = location.getLongitude();
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        return "long:" + decimalFormat.format(longitude);
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void stopUsingGPS() {
        Log.d(TAG, "stopUsingGPS ");
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               Log.e(TAG,"ACCESS_FINE_LOCATION permission not granted");
            }
            locationManager.removeUpdates(GeoLocation.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "onProviderDisabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged");
    }

}
