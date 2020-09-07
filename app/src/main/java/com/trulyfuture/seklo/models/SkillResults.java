package com.trulyfuture.seklo.models;

import android.content.Intent;

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
        @SerializedName("userSkillId")
        @Expose
        private Integer userSkillId;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("skillName")
        @Expose
        private String skillName;

        @SerializedName("skillId")
        private Integer skillId;

        public Integer getUserSkillId() {
            return userSkillId;
        }

        public void setUserSkillId(Integer userSkillId) {
            this.userSkillId = userSkillId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public Integer getSkillId() {
            return skillId;
        }

        public void setSkillId(Integer skillId) {
            this.skillId = skillId;
        }
    }
}
