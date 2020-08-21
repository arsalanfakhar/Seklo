package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersShort {
    @SerializedName("Fname")
    @Expose
    private String fname;

    @SerializedName("Lname")
    @Expose
    private String lname;

    @SerializedName("Number")
    @Expose
    private String number;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("Email")
    @Expose
    private String email;

    public UsersShort(String fname, String lname, String number, String password, String email) {
        this.fname = fname;
        this.lname = lname;
        this.number = number;
        this.password = password;
        this.email = email;
    }

    public UsersShort() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
