package com.example.ajhdsajkf;

public class Events {


    private String title;
    private String description;
    private String image_1;
    private String image_2;
    private String date;
    private String location;
    private String time;
    private String doctor;
    private String key;


    public Events()
    {

    }


    public Events(String title, String description, String image_1, String image_2, String date, String location, String time, String doctor, String key) {
        this.title = title;
        this.description = description;
        this.image_1 = image_1;
        this.image_2 = image_2;
        this.date = date;
        this.location = location;
        this.time = time;
        this.doctor = doctor;
        this.key = key;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_1() {
        return image_1;
    }

    public void setImage_1(String image_1) {
        this.image_1 = image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public void setImage_2(String image_2) {
        this.image_2 = image_2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
