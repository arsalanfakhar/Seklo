package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SkillResults {

    @SerializedName("results")
    @Expose
    private List<Skills> results = null;

    public List<Skills> getResults() {
        return results;
    }

    public void setResults(List<Skills> results) {
        this.results = results;
    }


    public static class Skills{
        @SerializedName("skillId")
        @Expose
        private Integer skillId;

        @SerializedName("skillName")
        @Expose
        private String skillName;

        public Integer getSkillId() {
            return skillId;
        }

        public void setSkillId(Integer skillId) {
            this.skillId = skillId;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

    }
}
