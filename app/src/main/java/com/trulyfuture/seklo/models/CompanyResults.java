package com.trulyfuture.seklo.models;
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






    public static class Company {

        @SerializedName("ID")
        @Expose
        private Integer iD;
        @SerializedName("Email")
        @Expose
        private String email;
        @SerializedName("Fname")
        @Expose
        private String fname;
        @SerializedName("Lname")
        @Expose
        private String lname;
        @SerializedName("Full_Name")
        @Expose
        private String fullName;
        @SerializedName("Number")
        @Expose
        private String number;
        @SerializedName("DateTime")
        @Expose
        private String dateTime;
        @SerializedName("Online")
        @Expose
        private Integer online;
        @SerializedName("Last_Online")
        @Expose
        private String lastOnline;
        @SerializedName("Country")
        @Expose
        private String country;

        public Integer getID() {
            return iD;
        }

        public void setID(Integer iD) {
            this.iD = iD;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public Integer getOnline() {
            return online;
        }

        public void setOnline(Integer online) {
            this.online = online;
        }

        public String getLastOnline() {
            return lastOnline;
        }

        public void setLastOnline(String lastOnline) {
            this.lastOnline = lastOnline;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }
}
