package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CvExistsResults {
    @SerializedName("results")
    @Expose
    private List<CvExists> results = null;

    public List<CvExists> getResults() {
        return results;
    }

    public void setResults(List<CvExists> results) {
        this.results = results;
    }

    public static class CvExists{
        @SerializedName("Reply")
        @Expose
        private Integer replyCode;

        public Integer getReplyCode() {
            return replyCode;
        }

        public void setReplyCode(Integer replyCode) {
            this.replyCode = replyCode;
        }
    }
}
