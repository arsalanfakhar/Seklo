package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResults {

    @SerializedName("results")
    @Expose
    private List<Users> userResultList = null;


    private int code=1;

    public List<Users> getUserResultList() {
        return userResultList;
    }

    public void setUserResultList(List<Users> userResultList) {
        this.userResultList = userResultList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
