package com.trulyfuture.seklo.models;
import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyResults {



        @SerializedName("results")
        @Expose
        private List<Company> results = null;

        public List<Company> getResults() {
            return results;
        }

        public void setResults(List<Company> results) {
            this.results = results;
        }






    public static class Company implements Serializable {
        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Website")
        @Expose
        private String website;
        @SerializedName("Number")
        @Expose
        private String number;
        @SerializedName("Overview")
        @Expose
        private String overview;
        @SerializedName("Founded")
        @Expose
        private String founded;
        @SerializedName("profilePic")
        @Expose
        private String profilePic;
        @SerializedName("numOfEmlpyees")
        @Expose
        private String numOfEmlpyees;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getFounded() {
            return founded;
        }

        public void setFounded(String founded) {
            this.founded = founded;
        }

        public String getProfilePic() {
            return profilePic;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public String getNumOfEmlpyees() {
            return numOfEmlpyees;
        }

        public void setNumOfEmlpyees(String numOfEmlpyees) {
            this.numOfEmlpyees = numOfEmlpyees;}

    }
}
