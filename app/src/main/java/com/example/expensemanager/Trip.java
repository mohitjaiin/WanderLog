package com.example.expensemanager;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private String name;
    private String location;



    public Trip(){ }


    public Trip(String name, String location) {
        this.name = name;
        this.location = location;

    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




    public int getId() {
        return id;
    }
}
