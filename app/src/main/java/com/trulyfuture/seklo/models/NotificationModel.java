package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationModel {
    @SerializedName("results")
    @Expose
    private List<NotificationResult> results = null;

    public List<NotificationResult> getResults() {
        return results;
    }

    public void setResults(List<NotificationResult> results) {
        this.results = results;
    }

    public static class NotificationResult implements Serializable{
        @SerializedName("notId")
        @Expose
        private Integer notId;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sendBy")
        @Expose
        private Integer sendBy;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("remove")
        @Expose
        private Integer remove;

        public Integer getNotId() {
            return notId;
        }

        public void setNotId(Integer notId) {
            this.notId = notId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getSendBy() {
            return sendBy;
        }

        public void setSendBy(Integer sendBy) {
            this.sendBy = sendBy;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getRemove() {
            return remove;
        }

        public void setRemove(Integer remove) {
            this.remove = remove;
        }
    }

}
