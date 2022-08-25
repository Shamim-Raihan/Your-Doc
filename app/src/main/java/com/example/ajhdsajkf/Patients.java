package com.example.ajhdsajkf;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Patients {

    private String firstname;
    private String lastname;
    private String age;
    private String phone;
    private String email;
    private String password;
    private String imageUri;
    private String Location;
    private String userCategory;
    private String events, dailytips;
    private String uid;
    private String fullName;


    public Patients() {
    }


    public Patients(String firstname, String lastname, String age, String phone, String email, String password, String imageUri, String location, String userCategory, String events, String dailytips, String uid, String fullName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
        this.Location = location;
        this.userCategory = userCategory;
        this.events = events;
        this.dailytips = dailytips;
        this.uid = uid;
        this.fullName = fullName;
    }



    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getDailytips() {
        return dailytips;
    }

    public void setDailytips(String dailytips) {
        this.dailytips = dailytips;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
