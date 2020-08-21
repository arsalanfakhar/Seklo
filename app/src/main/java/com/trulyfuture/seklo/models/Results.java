package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results {


    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
