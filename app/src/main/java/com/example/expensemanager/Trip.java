package com.example.expensemanager;

import java.io.Serializable;

public class Trip implements Serializable {
    private String name;
    private String location;
    private double budget;


    public Trip(){ }


    public Trip(String name, String location, double budget) {
        this.name = name;
        this.location = location;
        this.budget = budget;
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }


}
