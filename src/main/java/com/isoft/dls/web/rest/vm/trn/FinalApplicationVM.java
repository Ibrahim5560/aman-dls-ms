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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.domain.type.MultilingualJsonType;
import com.isoft.dls.web.rest.vm.util.ViewModel;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * A VM for the customer application summary application entity.
 */
@ApiModel(description = "service request application (trn_service_request) entity. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FinalApplicationVM implements ViewModel {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("customerNo")
    private Long customerNo;

    @JsonProperty("englishFullName")
    private String englishFullName;

    @JsonProperty("arabicFullName")
    private String arabicFullName;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("genderCode")
    private String genderCode;

    @JsonProperty("genderDescription")
    private MultilingualJsonType genderDescription;

    @JsonProperty("nationalityCode")
    private String nationalityCode;

    @JsonProperty("nationalityDescription")
    private MultilingualJsonType nationalityDescription;

    @JsonProperty("eidNumber")
    private String eidNumber;

    @JsonProperty("eidExpiryDate")
    private LocalDate eidExpiryDate;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("email")
    private String email;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    private String professionCode;

    private String sponsorTypeCode;

    public FinalApplicationVM() {
        // Default Constructor
    }

//    public FinalApplicationVM(CustomerDTO customerDTO, Long applicationId) {
//
//        this.setApplicationId(applicationId);
//        this.setCustomerNo(customerDTO.getId());
//        this.setEnglishFullName(StringUtil.formatString(customerDTO.getUserProfile().getEnglishFullName()));
//        this.setArabicFullName(customerDTO.getUserProfile().getArabicFullName());
//        this.setBirthdate(customerDTO.getBirthdate());
//
//        if(customerDTO.getUserProfile().getNationality() != null) {
//            this.setNationalityCode(customerDTO.getUserProfile().getNationalityCountryCode());
//            this.setNationalityDescription(customerDTO.getUserProfile().getNationality().getName());
//        }
//        this.setEidNumber(customerDTO.getEidNumber());
//        this.setEidExpiryDate(customerDTO.getEidExpiryDate());
//
//        if(customerDTO.getUserProfile().getProfession() != null) {
//            this.setProfessionCode(customerDTO.getUserProfile().getProfessionCode());
//        }
//
//        if(customerDTO.getSponsorType() != null) {
//            this.setSponsorTypeCode(customerDTO.getSponsorType().value());
//        }
//
//        this.setMobileNo(customerDTO.getUserProfile().getMobileNo());
//        this.setEmail(customerDTO.getUserProfile().getEmail());
//
//    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public String getEnglishFullName() {
        return englishFullName;
    }

    public void setEnglishFullName(String englishFullName) {
        this.englishFullName = englishFullName;
    }

    public String getArabicFullName() {
        return arabicFullName;
    }

    public void setArabicFullName(String arabicFullName) {
        this.arabicFullName = arabicFullName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public MultilingualJsonType getGenderDescription() {
        return genderDescription;
    }

    public void setGenderDescription(MultilingualJsonType genderDescription) {
        this.genderDescription = genderDescription;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public MultilingualJsonType getNationalityDescription() {
        return nationalityDescription;
    }

    public void setNationalityDescription(MultilingualJsonType nationalityDescription) {
        this.nationalityDescription = nationalityDescription;
    }

    public String getEidNumber() {
        return eidNumber;
    }

    public void setEidNumber(String eidNumber) {
        this.eidNumber = eidNumber;
    }

    public LocalDate getEidExpiryDate() {
        return eidExpiryDate;
    }

    public void setEidExpiryDate(LocalDate eidExpiryDate) {
        this.eidExpiryDate = eidExpiryDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
    }

    public String getSponsorTypeCode() {
        return sponsorTypeCode;
    }

    public void setSponsorTypeCode(String sponsorTypeCode) {
        this.sponsorTypeCode = sponsorTypeCode;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }


    @Override
    public String toString() {
        return "FinalApplicationVM{" +
            "applicationId=" + applicationId +
            ", customerNo=" + customerNo +
            ", englishFullName='" + englishFullName + '\'' +
            ", arabicFullName='" + arabicFullName + '\'' +
            ", birthdate=" + birthdate +
            ", genderCode='" + genderCode + '\'' +
            ", genderDescription=" + genderDescription +
            ", nationalityCode='" + nationalityCode + '\'' +
            ", nationalityDescription=" + nationalityDescription +
            ", eidNumber='" + eidNumber + '\'' +
            ", eidExpiryDate=" + eidExpiryDate +
            ", mobileNo='" + mobileNo + '\'' +
            ", email='" + email + '\'' +
            ", professionCode='" + professionCode + '\'' +
            ", sponsorTypeCode='" + sponsorTypeCode + '\'' +
            ", applicationCriteria=" + applicationCriteria +
            '}';
    }
}
