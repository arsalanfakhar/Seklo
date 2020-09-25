package com.trulyfuture.seklo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class CompanyHrResults {

    @SerializedName("results")
    @Expose
    private List<CompanyHr> hrList=null;

    public List<CompanyHr> getHrList() {
        return hrList;
    }

    public void setHrList(List<CompanyHr> hrList) {
        this.hrList = hrList;
    }

    public static class CompanyHr{

        @SerializedName("companyHrId")
        private Integer hrId;

        @SerializedName("companyId")
        private Integer companyId;

        @SerializedName("hrName")
        private String hrName;

        @SerializedName("hrDescription")
        private String hrDescrip;

        @SerializedName("regDate")
        private Date hrRegDate;

        @SerializedName("hrPic")
        private String hrImage;

        public Integer getHrId() {
            return hrId;
        }

        public void setHrId(Integer hrId) {
            this.hrId = hrId;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public String getHrName() {
            return hrName;
        }

        public void setHrName(String hrName) {
            this.hrName = hrName;
        }

        public String getHrDescrip() {
            return hrDescrip;
        }

        public void setHrDescrip(String hrDescrip) {
            this.hrDescrip = hrDescrip;
        }

        public Date getHrRegDate() {
            return hrRegDate;
        }

        public void setHrRegDate(Date hrRegDate) {
            this.hrRegDate = hrRegDate;
        }

        public String getHrImage() {
            return hrImage;
        }

        public void setHrImage(String hrImage) {
            this.hrImage = hrImage;
        }
    }

}
