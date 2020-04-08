///*
// * Copyright (c) ISOFT 2020.
// * Ferdous Tower (Takreer Building) , Salam Street
// * Abu Dhabi, United Arab Emirates
// * P.O. Box: 32326
// * All Rights Reserved.
// *
// * ver    Developer          	Date              Comments
// * ----- -----------------  	----------       -----------------
// * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
// */
//
//package com.isoft.dls.web.rest.vm.trn;
//
//import com.isoft.dls.common.util.StringUtil;
//import com.isoft.dls.domain.type.MultilingualJsonType;
//import com.isoft.dls.service.dto.um.CustomerDTO;
//import com.isoft.dls.web.rest.vm.util.ViewModel;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModel;
//
//import java.time.LocalDate;
//
///**
// * A VM for the CustomerProfileSummaryServiceRequest entity.
// */
//@ApiModel(description = "ServiceRequest (trn_service_request) entity. @author Mena Emiel.")
//public class CustomerProfileSummaryServiceRequestVM implements ViewModel {
//
//    @JsonProperty("applicationReferenceNo")
//    private Long applicationId;
//
//    @JsonProperty("customerNo")
//    private Long customerNo;
//
//    @JsonProperty("englishFullName")
//    private String englishFullName;
//
//    @JsonProperty("arabicFullName")
//    private String arabicFullName;
//
//    @JsonProperty("birthdate")
//    private LocalDate birthdate;
//
//    @JsonProperty("genderCode")
//    private String genderCode;
//
//    @JsonProperty("genderDescription")
//    private MultilingualJsonType genderDescription;
//
//    @JsonProperty("nationalityCode")
//    private String nationalityCode;
//
//    @JsonProperty("nationalityDescription")
//    private MultilingualJsonType nationalityDescription;
//
//    @JsonProperty("eidNumber")
//    private String eidNumber;
//
//    @JsonProperty("eidExpiryDate")
//    private LocalDate eidExpiryDate;
//
//    @JsonProperty("professionCode")
//    private String professionCode;
//
//    @JsonProperty("professionDescription")
//    private MultilingualJsonType professionDescription;
//
//    @JsonProperty("sponsorTypeCode")
//    private String sponsorTypeCode;
//
//    @JsonProperty("sponsorTypeDescription")
//    private MultilingualJsonType sponsorTypeDescription;
//
//    @JsonProperty("englishSponsorName")
//    private String englishSponsorName;
//
//    @JsonProperty("arabicSponsorName")
//    private String arabicSponsorName;
//
//    @JsonProperty("mobileNo")
//    private String mobileNo;
//
//    @JsonProperty("email")
//    private String email;
//
//    @JsonProperty("licenseCategoryCode")
//    private String licenseCategoryCode;
//
//    @JsonProperty("nationalityChanged")
//    private Boolean nationalityChanged;
//
//    public CustomerProfileSummaryServiceRequestVM() {
//        // Default constructor
//    }
//
//    public CustomerProfileSummaryServiceRequestVM(CustomerDTO customerDTO, Long applicationReferenceNo) {
//
//        this.setApplicationId(applicationReferenceNo);
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
//            this.setProfessionDescription(customerDTO.getUserProfile().getProfession().getName());
//        }
//
//        this.setEnglishSponsorName(StringUtil.formatString(customerDTO.getEnglishSponsorName()));
//        this.setArabicSponsorName(customerDTO.getArabicSponsorName());
//        this.setMobileNo(customerDTO.getUserProfile().getMobileNo());
//        this.setEmail(customerDTO.getUserProfile().getEmail());
//
//    }
//
//    public String getEnglishFullName() {
//        return englishFullName;
//    }
//
//    public void setEnglishFullName(String englishFullName) {
//        this.englishFullName = englishFullName;
//    }
//
//    public String getArabicFullName() {
//        return arabicFullName;
//    }
//
//    public void setArabicFullName(String arabicFullName) {
//        this.arabicFullName = arabicFullName;
//    }
//
//    public LocalDate getBirthdate() {
//        return birthdate;
//    }
//
//    public void setBirthdate(LocalDate birthdate) {
//        this.birthdate = birthdate;
//    }
//
//    public String getGenderCode() {
//        return genderCode;
//    }
//
//    public void setGenderCode(String genderCode) {
//        this.genderCode = genderCode;
//    }
//
//    public MultilingualJsonType getGenderDescription() {
//        return genderDescription;
//    }
//
//    public void setGenderDescription(MultilingualJsonType genderDescription) {
//        this.genderDescription = genderDescription;
//    }
//
//    public String getNationalityCode() {
//        return nationalityCode;
//    }
//
//    public void setNationalityCode(String nationalityCode) {
//        this.nationalityCode = nationalityCode;
//    }
//
//    public MultilingualJsonType getNationalityDescription() {
//        return nationalityDescription;
//    }
//
//    public void setNationalityDescription(MultilingualJsonType nationalityDescription) {
//        this.nationalityDescription = nationalityDescription;
//    }
//
//    public String getEidNumber() {
//        return eidNumber;
//    }
//
//    public void setEidNumber(String eidNumber) {
//        this.eidNumber = eidNumber;
//    }
//
//    public LocalDate getEidExpiryDate() {
//        return eidExpiryDate;
//    }
//
//    public void setEidExpiryDate(LocalDate eidExpiryDate) {
//        this.eidExpiryDate = eidExpiryDate;
//    }
//
//    public String getProfessionCode() {
//        return professionCode;
//    }
//
//    public void setProfessionCode(String professionCode) {
//        this.professionCode = professionCode;
//    }
//
//    public MultilingualJsonType getProfessionDescription() {
//        return professionDescription;
//    }
//
//    public void setProfessionDescription(MultilingualJsonType professionDescription) {
//        this.professionDescription = professionDescription;
//    }
//
//    public String getSponsorTypeCode() {
//        return sponsorTypeCode;
//    }
//
//    public void setSponsorTypeCode(String sponsorTypeCode) {
//        this.sponsorTypeCode = sponsorTypeCode;
//    }
//
//    public MultilingualJsonType getSponsorTypeDescription() {
//        return sponsorTypeDescription;
//    }
//
//    public void setSponsorTypeDescription(MultilingualJsonType sponsorTypeDescription) {
//        this.sponsorTypeDescription = sponsorTypeDescription;
//    }
//
//    public String getEnglishSponsorName() {
//        return englishSponsorName;
//    }
//
//    public void setEnglishSponsorName(String englishSponsorName) {
//        this.englishSponsorName = englishSponsorName;
//    }
//
//    public String getArabicSponsorName() {
//        return arabicSponsorName;
//    }
//
//    public void setArabicSponsorName(String arabicSponsorName) {
//        this.arabicSponsorName = arabicSponsorName;
//    }
//
//    public String getMobileNo() {
//        return mobileNo;
//    }
//
//    public void setMobileNo(String mobileNo) {
//        this.mobileNo = mobileNo;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Long getCustomerNo() {
//        return customerNo;
//    }
//
//    public void setCustomerNo(Long customerNo) {
//        this.customerNo = customerNo;
//    }
//
//    public Long getApplicationId() {
//        return applicationId;
//    }
//
//    public void setApplicationId(Long applicationId) {
//        this.applicationId = applicationId;
//    }
//
//    public String getLicenseCategoryCode() {
//        return licenseCategoryCode;
//    }
//
//    public void setLicenseCategoryCode(String licenseCategoryCode) {
//        this.licenseCategoryCode = licenseCategoryCode;
//    }
//
//    public Boolean getNationalityChanged() {
//        return nationalityChanged;
//    }
//
//    public void setNationalityChanged(Boolean nationalityChanged) {
//        this.nationalityChanged = nationalityChanged;
//    }
//
//    @Override
//    public String toString() {
//        return "CustomerProfileSummaryServiceRequestVM{" +
//            "applicationId=" + applicationId +
//            ", customerNo=" + customerNo +
//            ", englishFullName='" + englishFullName + '\'' +
//            ", arabicFullName='" + arabicFullName + '\'' +
//            ", birthdate=" + birthdate +
//            ", genderCode='" + genderCode + '\'' +
//            ", genderDescription=" + genderDescription +
//            ", nationalityCode='" + nationalityCode + '\'' +
//            ", nationalityDescription=" + nationalityDescription +
//            ", eidNumber='" + eidNumber + '\'' +
//            ", eidExpiryDate=" + eidExpiryDate +
//            ", professionCode='" + professionCode + '\'' +
//            ", professionDescription=" + professionDescription +
//            ", sponsorTypeCode='" + sponsorTypeCode + '\'' +
//            ", sponsorTypeDescription=" + sponsorTypeDescription +
//            ", englishSponsorName='" + englishSponsorName + '\'' +
//            ", arabicSponsorName='" + arabicSponsorName + '\'' +
//            ", mobileNo='" + mobileNo + '\'' +
//            ", email='" + email + '\'' +
//            ", licenseCategoryCode='" + licenseCategoryCode + '\'' +
//            '}';
//    }
//}
