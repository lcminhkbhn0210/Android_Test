package com.example.sqlitedemo.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String title,date,category,price;

    public Item(int id, String title, String category, String price, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;
        this.price = price;
    }
    public Item(){}

    public Item(String title, String category, String price, String date) {
        this.title = title;
        this.date = date;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
