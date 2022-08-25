package com.example.ajhdsajkf;

public class DailyTips {


    private String title;
    private String image;
    private String description;
    private String key1;


    public DailyTips() {
    }

    public DailyTips(String title, String image, String description, String key1) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.key1 = key1;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }
}
