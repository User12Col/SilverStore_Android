package com.example.silverstore_app.model;

public class Cart {
    private int cartID;
    private int proID;
    private String proName;
    private String proIMG;
    private int unitPrice;
    private int accID;

    public Cart() {
    }

    public Cart(int cartID, int proID, String proName, String proIMG, int unitPrice, int accID) {
        this.cartID = cartID;
        this.proID = proID;
        this.proName = proName;
        this.proIMG = proIMG;
        this.unitPrice = unitPrice;
        this.accID = accID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
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

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }
}
