package com.example.ajhdsajkf.Model;

public class Users {

    private String uid;
    private String firstname;
    private String imageUri;
    private String status;



    public Users(String uid, String firstname, String imageUri, String status) {
        this.uid = uid;
        this.firstname = firstname;
        this.imageUri = imageUri;
        this.status = status;
    }

    public String getId() {
        return uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
