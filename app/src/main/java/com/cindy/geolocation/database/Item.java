package com.cindy.geolocation.database;

/**
 * Created by Cindyyh_Chou on 2017/4/26.
 */

public class Item {
    private long id;
    private String name;
    private int age;

    public void setId(long id){
        this.id=id;
    }

    public long getId(){
        return id;
    }

    public void setName(String  name){
        this.name=name;
    }

    public String getNmae(){
        return  name;
    }

    public void setAge(int age){
        this.age=age;
    }

    public int getAge(){
        return age;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return "Name: "+name+",Age: "+age;
    }

}
