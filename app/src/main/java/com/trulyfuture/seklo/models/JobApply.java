package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobApply {

    @SerializedName("DBobj")
    @Expose
    private JobDBObj dBObj;

    @SerializedName("infoObj")
    @Expose
    private InfoObj infoObj;

    public JobDBObj getdBObj() {
        return dBObj;
    }

    public void setdBObj(JobDBObj dBObj) {
        this.dBObj = dBObj;
    }

    public InfoObj getInfoObj() {
        return infoObj;
    }

    public void setInfoObj(InfoObj infoObj) {
        this.infoObj = infoObj;
    }

    public static class JobDBObj {

        @SerializedName("userId")
        @Expose
        private Integer userId;

        @SerializedName("jobId")
        @Expose
        private Integer jobId;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getJobId() {
            return jobId;
        }

        public void setJobId(Integer jobId) {
            this.jobId = jobId;
        }
    }

    public static class InfoObj {

        @SerializedName("userId")
        @Expose
        private Integer userId;

        @SerializedName("companyName")
        @Expose
        private String companyName;

        @SerializedName("jobName")
        @Expose
        private String jobName;

        @SerializedName("jobLocation")
        @Expose
        private String jobLocation;

        @SerializedName("jobEmail")
        @Expose
        private String jobEmail;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public String getJobLocation() {
            return jobLocation;
        }

        public void setJobLocation(String jobLocation) {
            this.jobLocation = jobLocation;
        }

        public String getJobEmail() {
            return jobEmail;
        }

        public void setJobEmail(String jobEmail) {
            this.jobEmail = jobEmail;
        }
    }



}
