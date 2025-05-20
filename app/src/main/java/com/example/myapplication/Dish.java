package com.example.myapplication;

import java.io.Serializable;

public class Dish implements Serializable {
    private static final String[] MUST_IMGS = {"must_buy_one", "must_buy_two"};
    private static final String[] RECOM_IMGS = {"recom_one", "recom_two"};

    public Dish(String category, String name, String sales, String price, int img) {
        this.Category = category;
        this.name = name;
        this.sales = sales;
        this.price = price;
        this.img = img;
    }

    public Dish() {}

    private static final long serialVersionUID = 1L;
    private String name;
    private String sales;
    private String price;
    private String Category;
    private int img;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return Category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setCategory(String category) {
        Category = category;
    }
}