package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HRServices implements Serializable{

    @SerializedName("DBObj")
    @Expose
    private DBObj dBObj;

    @SerializedName("EmailObj")
    @Expose
    private EmailObj emailObj;

    public DBObj getdBObj() {
        return dBObj;
    }

    public void setdBObj(DBObj dBObj) {
        this.dBObj = dBObj;
    }

    public EmailObj getEmailObj() {
        return emailObj;
    }

    public void setEmailObj(EmailObj emailObj) {
        this.emailObj = emailObj;
    }

    public static class EmailObj implements Serializable {

        @SerializedName("User_Email")
        @Expose
        private String userEmail;

        @SerializedName("HR_Email")
        @Expose
        private String hREmail;

        @SerializedName("User_Name")
        @Expose
        private String userName;

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String gethREmail() {
            return hREmail;
        }

        public void sethREmail(String hREmail) {
            this.hREmail = hREmail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class DBObj implements Serializable {

        @SerializedName("HR_ID")
        @Expose
        private Integer hRID;
        @SerializedName("User_ID")
        @Expose
        private Integer userID;
        @SerializedName("Payment")
        @Expose
        private Integer payment;
        @SerializedName("Currency")
        @Expose
        private String currency;
        @SerializedName("Days")
        @Expose
        private Integer days;


        @SerializedName("Resume_Write")
        private String resumeType;

        @SerializedName("Company")
        private String companyName;

        @SerializedName("Job")
        private String jobName;

        @SerializedName("TimeHours")
        private String timeHours;

        public String getTimeHours() {
            return timeHours;
        }

        public void setTimeHours(String timeHours) {
            this.timeHours = timeHours;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getResumeType() {
            return resumeType;
        }

        public void setResumeType(String resumeType) {
            this.resumeType = resumeType;
        }

        public Integer gethRID() {
            return hRID;
        }

        public void sethRID(Integer hRID) {
            this.hRID = hRID;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public Integer getPayment() {
            return payment;
        }

        public void setPayment(Integer payment) {
            this.payment = payment;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }
    }
}
