package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServicesResults {

    @SerializedName("results")
    @Expose
    private List<Services> results = null;

    public List<Services> getResults() {
        return results;
    }

    public void setResults(List<Services> results) {
        this.results = results;
    }

    public static class Services{

        @SerializedName("OptionID")
        @Expose
        private Integer optionID;
        @SerializedName("Services_ID")
        @Expose
        private Integer servicesID;
        @SerializedName("Services_Names")
        @Expose
        private String servicesNames;
        @SerializedName("Days")
        @Expose
        private Integer days;
        @SerializedName("Time")
        @Expose
        private String time;
        @SerializedName("Total_In_PKR")
        @Expose
        private Double totalInPKR;
        @SerializedName("Total_In_Dollar")
        @Expose
        private Double totalInDollar;

        public Integer getOptionID() {
            return optionID;
        }

        public void setOptionID(Integer optionID) {
            this.optionID = optionID;
        }

        public Integer getServicesID() {
            return servicesID;
        }

        public void setServicesID(Integer servicesID) {
            this.servicesID = servicesID;
        }

        public String getServicesNames() {
            return servicesNames;
        }

        public void setServicesNames(String servicesNames) {
            this.servicesNames = servicesNames;
        }

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Double getTotalInPKR() {
            return totalInPKR;
        }

        public void setTotalInPKR(Double totalInPKR) {
            this.totalInPKR = totalInPKR;
        }

        public Double getTotalInDollar() {
            return totalInDollar;
        }

        public void setTotalInDollar(Double totalInDollar) {
            this.totalInDollar = totalInDollar;
        }
    }
}
