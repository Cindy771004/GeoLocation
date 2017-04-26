package com.cindy.geolocation.database;

/**
 * Created by Cindyyh_Chou on 2017/4/26.
 */

public class Item {
    private long id;
    private double lat;
    private double lon;

    public void setId(long id){
        this.id=id;
    }

    public long getId(){
        return id;
    }

    public void setLat(double lat){
        this.lat=lat;
    }

    public double getLat(){
        return  lat;
    }

    public void setLon(double lon){
        this.lon=lon;
    }

    public double getLon(){
        return lon;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "("+lat+","+lon+")";
    }

}
