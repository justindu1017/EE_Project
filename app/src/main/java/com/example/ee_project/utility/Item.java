package com.example.ee_project.utility;

import java.util.Date;

public class Item {


    String articaluserName;
    String articalTitle;
    String articalContent;
    Date articalDate;

    public Item(String articaluserName, String articalTitle, String articalContent, Date articalDate) {
        this.articaluserName = articaluserName;
        this.articalTitle = articalTitle;
        this.articalContent = articalContent;
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

    public String getArticalContent() {
        return articalContent;
    }

    public void setArticalContent(String articalContent) {
        this.articalContent = articalContent;
    }

    public Date getArticalDate() {
        return articalDate;
    }

    public void setArticalDate(Date articalDate) {
        this.articalDate = articalDate;
    }
}
