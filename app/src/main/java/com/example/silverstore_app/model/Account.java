package com.example.silverstore_app.model;

import java.io.Serializable;

public class Account implements Serializable {
    private int accID;
    private String userName;
    private String password;

    public Account(int accID, String userName, String password) {
        this.accID = accID;
        this.userName = userName;
        this.password = password;
    }

    public Account() {
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
