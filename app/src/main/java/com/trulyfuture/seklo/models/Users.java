package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("ID")
    @Expose
    private Integer id;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Fname")
    @Expose
    private String fname;
    @SerializedName("Lname")
    @Expose
    private String lname;
    @SerializedName("Full_Name")
    @Expose
    private String fullName;
    @SerializedName("Number")
    @Expose
    private String number;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("Online")
    @Expose
    private Integer online;
    @SerializedName("Last_Online")
    @Expose
    private String lastOnline;
    @SerializedName("Country")
    @Expose
    private String country;

    //Only for login API
    @SerializedName("str")
    @Expose
    private String str;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("userPic")
    @Expose
    private String userImage;



    public Integer getID() {
        return id;
    }

    public void setID(Integer iD) {
        this.id = iD;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
