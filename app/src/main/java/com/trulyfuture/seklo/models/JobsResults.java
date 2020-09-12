package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JobsResults {

    @SerializedName("results")
    @Expose
    private List<Jobs> results = null;

    public List<Jobs> getResults() {
        return results;
    }

    public void setResults(List<Jobs> results) {
        this.results = results;
    }

    public static class Jobs implements Serializable {
        @SerializedName("Job_ID")
        @Expose
        private Integer jobID;

        @SerializedName("JobTitle")
        @Expose
        private String jobTitle;

        @SerializedName("jobDescription")
        @Expose
        private String jobDescription;

        @SerializedName("paid")
        @Expose
        private Integer paid;

        @SerializedName("jobRequirements")
        @Expose
        private String jobRequirements;

        @SerializedName("PostDate")
        @Expose
        private String postDate;

        @SerializedName("ExpiresDate")
        @Expose
        private String expiresDate;

        @SerializedName("Approved")
        @Expose
        private Integer approved;

        @SerializedName("Filled")
        @Expose
        private Integer filled;

        @SerializedName("Removed")
        @Expose
        private Integer removed;

        @SerializedName("Featured")
        @Expose
        private Integer featured;

        @SerializedName("Min_Pay")
        @Expose
        private String minPay;

        @SerializedName("Max_Pay")
        @Expose
        private String maxPay;

        @SerializedName("Currency")
        @Expose
        private String currency;

        @SerializedName("Company_ID")
        @Expose
        private Integer companyID;

        @SerializedName("Company_Name")
        @Expose
        private String companyName;

        @SerializedName("companyEmail")
        @Expose
        private String companyEmail;

        @SerializedName("profilePic")
        @Expose
        private String profilePic;

        @SerializedName("Location")
        @Expose
        private String location;

        @SerializedName("JobType")
        @Expose
        private String jobType;

        @SerializedName("Category")
        @Expose
        private String category;

        @SerializedName("Contracts")
        @Expose
        private String contracts;

        @SerializedName("appliedJobsId")
        @Expose
        private Integer appliedJobsId;

        public Integer getJobID() {
            return jobID;
        }

        public void setJobID(Integer jobID) {
            this.jobID = jobID;
        }

        public String getJobTitle() {
            return jobTitle;
        }

        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }

        public String getJobDescription() {
            return jobDescription;
        }

        public void setJobDescription(String jobDescription) {
            this.jobDescription = jobDescription;
        }

        public Integer getPaid() {
            return paid;
        }

        public void setPaid(Integer paid) {
            this.paid = paid;
        }

        public String getJobRequirements() {
            return jobRequirements;
        }

        public void setJobRequirements(String jobRequirements) {
            this.jobRequirements = jobRequirements;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getExpiresDate() {
            return expiresDate;
        }

        public void setExpiresDate(String expiresDate) {
            this.expiresDate = expiresDate;
        }

        public Integer getApproved() {
            return approved;
        }

        public void setApproved(Integer approved) {
            this.approved = approved;
        }

        public Integer getFilled() {
            return filled;
        }

        public void setFilled(Integer filled) {
            this.filled = filled;
        }

        public Integer getRemoved() {
            return removed;
        }

        public void setRemoved(Integer removed) {
            this.removed = removed;
        }

        public Integer getFeatured() {
            return featured;
        }

        public void setFeatured(Integer featured) {
            this.featured = featured;
        }

        public String getMinPay() {
            return minPay;
        }

        public void setMinPay(String minPay) {
            this.minPay = minPay;
        }

        public String getMaxPay() {
            return maxPay;
        }

        public void setMaxPay(String maxPay) {
            this.maxPay = maxPay;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Integer getCompanyID() {
            return companyID;
        }

        public void setCompanyID(Integer companyID) {
            this.companyID = companyID;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyEmail() {
            return companyEmail;
        }

        public void setCompanyEmail(String companyEmail) {
            this.companyEmail = companyEmail;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getContracts() {
            return contracts;
        }

        public void setContracts(String contracts) {
            this.contracts = contracts;
        }

        public Integer getAppliedJobsId() {
            return appliedJobsId;
        }

        public void setAppliedJobsId(Integer appliedJobsId) {
            this.appliedJobsId = appliedJobsId;
        }
    }
}
