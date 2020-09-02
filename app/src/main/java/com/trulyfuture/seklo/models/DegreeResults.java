package com.trulyfuture.seklo.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DegreeResults {

    @SerializedName("results")
    @Expose
    private List<Degrees> results = null;

    public List<Degrees> getResults() {
        return results;
    }

    public void setResults(List<Degrees> results) {
        this.results = results;
    }

    public static class Degrees{

        @SerializedName("degreeId")
        @Expose
        private Integer degreeId;

        @SerializedName("degreeName")
        @Expose
        private String degreeName;


        public Integer getDegreeId() {
            return degreeId;
        }

        public void setDegreeId(Integer degreeId) {
            this.degreeId = degreeId;
        }

        public String getDegreeName() {
            return degreeName;
        }

        public void setDegreeName(String degreeName) {
            this.degreeName = degreeName;
        }


        @NonNull
        @Override
        public String toString() {
            return degreeName;
        }
    }
}
