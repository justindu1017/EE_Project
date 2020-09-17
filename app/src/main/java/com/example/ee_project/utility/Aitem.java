package com.example.ee_project.utility;

public class Aitem {


    String userName;
    String articalTitle;
    String articalContent;

    public Aitem(String userName, String articalTitle, String articalContent) {
        this.userName = userName;
        this.articalTitle = articalTitle;
        this.articalContent = articalContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
