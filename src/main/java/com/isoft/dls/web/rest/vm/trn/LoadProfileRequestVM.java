/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.trn;

import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * A VM of Load Profile request
 */
@ApiModel(description = "load profile request . @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoadProfileRequestVM implements ViewModel {

    @JsonProperty("userProfileNo")
    private Long userProfileNo;

    @JsonProperty("lastEnglishName")
    private String lastEnglishName;

    @JsonProperty("applicationTypeRef")
    private Long applicationTypeId;

    @JsonProperty("emiratesId")
    private String emiratesId;

    @JsonProperty("expiryDate")
    private LocalDate expiryDate;

    @JsonProperty("trafficCodeNo")
    private Long trafficCodeNo;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("licenseNo")
    private String licenseNo;

    @JsonProperty("licenseIssueDate")
    private String licenseIssueDate;

    @JsonProperty("birthDate")
    private LocalDate birthDate;

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getTrafficCodeNo() {
        return trafficCodeNo;
    }

    public void setTrafficCodeNo(Long trafficCodeNo) {
        this.trafficCodeNo = trafficCodeNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(String licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getUserProfileNo() {
        return userProfileNo;
    }

    public void setUserProfileNo(Long userProfileNo) {
        this.userProfileNo = userProfileNo;
    }

    public String getLastEnglishName() {
        return lastEnglishName;
    }

    public void setLastEnglishName(String lastEnglishName) {
        this.lastEnglishName = lastEnglishName;
    }

    @Override
    public String toString() {
        return "LoadProfileRequestVM{" +
            "userProfileNo=" + userProfileNo +
            ", lastEnglishName='" + lastEnglishName + '\'' +
            ", applicationTypeId=" + applicationTypeId +
            ", emiratesId='" + emiratesId + '\'' +
            ", expiryDate='" + expiryDate + '\'' +
            ", trafficCodeNo=" + trafficCodeNo +
            ", mobileNo='" + mobileNo + '\'' +
            ", licenseNo=" + licenseNo +
            ", licenseIssueDate='" + licenseIssueDate + '\'' +
            ", birthDate='" + birthDate + '\'' +
            '}';
    }
}
