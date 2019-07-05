package com.example.testapp;

import android.graphics.drawable.Drawable;

public class Product {
    private String name;
    private int id;
    private String description;
    private Drawable drawable;

    public Product(String name, int id, String description)//, Drawable drawable)
    {
        this.name = name;
        this.id = id;
        this.description = description;
        //this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
