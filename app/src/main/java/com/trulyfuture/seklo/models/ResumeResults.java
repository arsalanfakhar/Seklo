package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResumeResults {

    @SerializedName("results")
    @Expose
    private List<Resume> results = null;

    public List<Resume> getResults() {
        return results;
    }

    public void setResults(List<Resume> results) {
        this.results = results;
    }

    public static class Resume{

        @SerializedName("Resume_ID")
        @Expose
        private Integer resumeID;

        @SerializedName("User_ID")
        @Expose
        private Integer userID;

        @SerializedName("Upload_Date")
        @Expose
        private String uploadDate;

        @SerializedName("Resume_Name")
        @Expose
        private String resumeName;

        @SerializedName("Resume_Size")
        @Expose
        private String resumeSize;

        @SerializedName("Resume_Type")
        @Expose
        private String resumeType;

        @SerializedName("Resume")
        @Expose
        private String resume;

        public Integer getResumeID() {
            return resumeID;
        }

        public void setResumeID(Integer resumeID) {
            this.resumeID = resumeID;
        }

        public Integer getUserID() {
            return userID;
        }

        public void setUserID(Integer userID) {
            this.userID = userID;
        }

        public String getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
        }

        public String getResumeName() {
            return resumeName;
        }

        public void setResumeName(String resumeName) {
            this.resumeName = resumeName;
        }

        public String getResumeSize() {
            return resumeSize;
        }

        public void setResumeSize(String resumeSize) {
            this.resumeSize = resumeSize;
        }

        public String getResumeType() {
            return resumeType;
        }

        public void setResumeType(String resumeType) {
            this.resumeType = resumeType;
        }

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }
    }
}
