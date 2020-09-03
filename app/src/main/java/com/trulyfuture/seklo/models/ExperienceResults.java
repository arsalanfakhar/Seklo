package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExperienceResults {
    @SerializedName("results")
    @Expose
    private List<Experience> results = null;

    public List<Experience> getResults() {
        return results;
    }

    public void setResults(List<Experience> results) {
        this.results = results;
    }


    public static class Experience{
        @SerializedName("expId")
        @Expose
        private Integer expId;
        @SerializedName("Title")
        @Expose
        private String title;
        @SerializedName("Company")
        @Expose
        private String company;
        @SerializedName("startDate")
        @Expose
        private Integer startDate;
        @SerializedName("endDate")
        @Expose
        private Integer endDate;
        @SerializedName("empType")
        @Expose
        private Integer empType;
        @SerializedName("empDes")
        @Expose
        private String empDes;

        public Integer getExpId() {
            return expId;
        }

        public void setExpId(Integer expId) {
            this.expId = expId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public Integer getStartDate() {
            return startDate;
        }

        public void setStartDate(Integer startDate) {
            this.startDate = startDate;
        }

        public Integer getEndDate() {
            return endDate;
        }

        public void setEndDate(Integer endDate) {
            this.endDate = endDate;
        }

        public Integer getEmpType() {
            return empType;
        }

        public void setEmpType(Integer empType) {
            this.empType = empType;
        }

        public String getEmpDes() {
            return empDes;
        }

        public void setEmpDes(String empDes) {
            this.empDes = empDes;
        }
    }
}
