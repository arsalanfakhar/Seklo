
package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HrResults {

    @SerializedName("results")
    @Expose
    private List<Hr> hrList=null;

    public List<Hr> getHrList() {
        return hrList;
    }

    public void setHrList(List<Hr> hrList) {
        this.hrList = hrList;
    }

    public static class Hr{
        @SerializedName("ID")
        @Expose
        private Integer id;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("Full_Name")
        @Expose
        private String fullName;

        @SerializedName("Profile_Pic")
        @Expose
        private String profilePic;

        @SerializedName("Overview")
        @Expose
        private String overview;

        @SerializedName("Number")
        @Expose
        private String number;

        @SerializedName("Email")
        @Expose
        private String email;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
