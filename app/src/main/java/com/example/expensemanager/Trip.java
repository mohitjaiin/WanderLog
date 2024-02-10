package com.example.expensemanager;

import java.io.Serializable;

public class Trip implements Serializable {
    private String name;
    private String location;
    private double budget;

    public Trip(String name, String location, double budget) {
        this.name = name;
        this.location = location;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getBudget() {
        return budget;
    }
}
