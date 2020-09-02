package com.trulyfuture.seklo.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudyFieldsResults {

    @SerializedName("results")
    @Expose
    private List<StudyFields> results = null;

    public List<StudyFields> getResults() {
        return results;
    }

    public void setResults(List<StudyFields> results) {
        this.results = results;
    }

    public static class StudyFields{

        @SerializedName("studyId")
        @Expose
        private Integer studyId;

        @SerializedName("studyName")
        @Expose
        private String studyName;

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

        @NonNull
        @Override
        public String toString() {
            return studyName;
        }
    }
}
