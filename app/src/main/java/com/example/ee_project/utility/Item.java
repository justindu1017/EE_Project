package com.example.ee_project.utility;

import java.util.Date;

public class Item {


    String articaluserName;
    String articalTitle;
    int articalAID;
    String articalDate;
    public Item(int articalAID, String articaluserName, String articalTitle, String articalDate) {
        this.articaluserName = articaluserName;
        this.articalTitle = articalTitle;
        this.articalAID = articalAID;
        this.articalDate = articalDate;
    }

    public String getArticaluserName() {
        return articaluserName;
    }

    public void setArticaluserName(String articaluserName) {
        this.articaluserName = articaluserName;
    }

    public String getArticalTitle() {
        return articalTitle;
    }

    public void setArticalTitle(String articalTitle) {
        this.articalTitle = articalTitle;
    }

    public int getArticalAID() {
        return articalAID;
    }

    public void setArticalAID(int articalAID) {
        this.articalAID = articalAID;
    }

    public String getArticalDate() {
        return articalDate;
    }

    public void setArticalDate(String articalDate) {
        this.articalDate = articalDate;
    }
}
