package com.example.silverstore_app.model;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {
    private int proID;
    private String proName;
    private String proIMG;
    private int quantity;
    private int unitPrice;
    private int categoryID;
    private String discription;

    public Product(){

    }

    public Product(int proID, String proName, String proIMG, int quantity, int unitPrice, int categoryID, String desciption) {
        this.proID = proID;
        this.proName = proName;
        this.proIMG = proIMG;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.categoryID = categoryID;
        this.discription = desciption;
    }

    public String getDesciption() {
        return discription;
    }

    public void setDesciption(String desciption) {
        this.discription = desciption;
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProIMG() {
        return proIMG;
    }

    public void setProIMG(String proIMG) {
        this.proIMG = proIMG;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public int compareTo(Product product) {
        return this.getUnitPrice() - product.getUnitPrice();
    }
}
