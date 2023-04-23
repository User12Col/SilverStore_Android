package com.example.silverstore_app.model;

public class Notification {
    private int notiID;
    private int accID;
    private String content;
    private String day;
    private String time;

    public Notification() {
    }

    public Notification(int notiID, int accID, String content, String date, String time) {
        this.notiID = notiID;
        this.accID = accID;
        this.content = content;
        this.day = date;
        this.time = time;
    }

    public int getNotiID() {
        return notiID;
    }

    public void setNotiID(int notiID) {
        this.notiID = notiID;
    }

    public int getAccID() {
        return accID;
    }

    public void setAccID(int accID) {
        this.accID = accID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return day;
    }

    public void setDate(String date) {
        this.day = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
