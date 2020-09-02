package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EducationResults {


    @SerializedName("results")
    @Expose
    private List<EducationModel> results = null;

    public List<EducationModel> getResults() {
        return results;
    }

    public void setResults(List<EducationModel> results) {
        this.results = results;
    }

    public static class EducationModel{

        @SerializedName("edId")
        @Expose
        private Integer edId;

        @SerializedName("uniName")
        @Expose
        private String uniName;
        @SerializedName("degreeId")
        @Expose
        private Integer degreeId;
        @SerializedName("degreeName")
        @Expose
        private String degreeName;
        @SerializedName("studyId")
        @Expose
        private Integer studyId;
        @SerializedName("studyName")
        @Expose
        private String studyName;
        @SerializedName("startYear")
        @Expose
        private String startYear;
        @SerializedName("endYear")
        @Expose
        private String endYear;


        public Integer getEdId() {
            return edId;
        }

        public void setEdId(Integer edId) {
            this.edId = edId;
        }

        public String getUniName() {
            return uniName;
        }

        public void setUniName(String uniName) {
            this.uniName = uniName;
        }

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

        public Integer getStudyId() {
            return studyId;
        }

        public void setStudyId(Integer studyId) {
            this.studyId = studyId;
        }

        public String getStudyName() {
            return studyName;
        }

        public void setStudyName(String studyName) {
            this.studyName = studyName;
        }

        public String getStartYear() {
            return startYear;
        }

        public void setStartYear(String startYear) {
            this.startYear = startYear;
        }

        public String getEndYear() {
            return endYear;
        }

        public void setEndYear(String endYear) {
            this.endYear = endYear;
        }
    }

}
