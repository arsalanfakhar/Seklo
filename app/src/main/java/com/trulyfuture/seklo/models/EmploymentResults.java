package com.trulyfuture.seklo.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmploymentResults {
    @SerializedName("results")
    @Expose
    private List<EmploymentType> results = null;

    public List<EmploymentType> getResults() {
        return results;
    }

    public void setResults(List<EmploymentType> results) {
        this.results = results;
    }
    public static class  EmploymentType{
        @SerializedName("List_ID")
        @Expose
        private Integer listID;
        @SerializedName("ListTypeID")
        @Expose
        private Integer listTypeID;
        @SerializedName("Description")
        @Expose
        private String description;

        public Integer getListID() {
            return listID;
        }

        public void setListID(Integer listID) {
            this.listID = listID;
        }

        public Integer getListTypeID() {
            return listTypeID;
        }

        public void setListTypeID(Integer listTypeID) {
            this.listTypeID = listTypeID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @NonNull
        @Override
        public String toString() {
            return description.toString();
        }
    }
}