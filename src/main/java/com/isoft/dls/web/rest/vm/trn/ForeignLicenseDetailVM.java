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
//import com.isoft.dls.domain.type.CountryJsonType;
//import com.isoft.dls.domain.type.ForeignLicenseCategoryDetailsJsonType;
//import com.isoft.dls.service.dto.sys.SysDomainValueDTO;
//import com.isoft.dls.web.rest.vm.util.ViewModel;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModel;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@ApiModel(description = "Foreign License Detail. @author Mohammad Qasim.")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class ForeignLicenseDetailVM implements ViewModel {
//
//    @JsonProperty("licenseNo")
//    private String licenseNo;
//
//    @JsonProperty("templateId")
//    private Long templateId;
//
//    @JsonProperty("validity")
//    private SysDomainValueDTO validity;
//
//    @JsonProperty("experience")
//    private SysDomainValueDTO experience;
//
//    @JsonProperty("issueDate")
//    private LocalDate issueDate;
//
//    @JsonProperty("expiryDate")
//    private LocalDate expiryDate;
//
//    @JsonProperty("requestDate")
//    private LocalDateTime requestDate;
//
//    @JsonProperty("issuedFromCountryDetails")
//    private CountryJsonType issuedFromCountryDetails;
//
//    @JsonProperty("categoryDetails")
//    private List<ForeignLicenseCategoryDetailsJsonType> foreignLicenseCategoryDetails;
//
//    @JsonProperty("driverNationality")
//    private NationalityVM driverNationality;
//
//    @JsonProperty("frontSideImage")
//    private String frontSideImage;
//
//    @JsonProperty("backSideImage")
//    private String backSideImage;
//
//    @JsonProperty("translationDocument")
//    private String translationDocument;
//
//    public String getLicenseNo() {
//        return licenseNo;
//    }
//
//    public void setLicenseNo(String licenseNo) {
//        this.licenseNo = licenseNo;
//    }
//
//    public Long getTemplateId() {
//        return templateId;
//    }
//
//    public void setTemplateId(Long templateId) {
//        this.templateId = templateId;
//    }
//
//    public LocalDateTime getRequestDate() {
//        return requestDate;
//    }
//
//    public void setRequestDate(LocalDateTime requestDate) {
//        this.requestDate = requestDate;
//    }
//
//    public LocalDate getIssueDate() {
//        return issueDate;
//    }
//
//    public void setIssueDate(LocalDate statusDate) {
//        this.issueDate = statusDate;
//    }
//
//    public LocalDate getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(LocalDate expiryDate) {
//        this.expiryDate = expiryDate;
//    }
//
//    public CountryJsonType getIssuedFromCountryDetails() {
//        return issuedFromCountryDetails;
//    }
//
//    public void setIssuedFromCountryDetails(CountryJsonType issuedFromCountryDetails) {
//        this.issuedFromCountryDetails = issuedFromCountryDetails;
//    }
//
//    public SysDomainValueDTO getValidity() {
//        return validity;
//    }
//
//    public void setValidity(SysDomainValueDTO validity) {
//        this.validity = validity;
//    }
//
//    public SysDomainValueDTO getExperience() {
//        return experience;
//    }
//
//    public void setExperience(SysDomainValueDTO experience) {
//        this.experience = experience;
//    }
//
//    public String getFrontSideImage() {
//        return frontSideImage;
//    }
//
//    public void setFrontSideImage(String frontSideImage) {
//        this.frontSideImage = frontSideImage;
//    }
//
//    public String getBackSideImage() {
//        return backSideImage;
//    }
//
//    public void setBackSideImage(String backSideImage) {
//        this.backSideImage = backSideImage;
//    }
//
//    public String getTranslationDocument() {
//        return translationDocument;
//    }
//
//    public void setTranslationDocument(String translationDocument) {
//        this.translationDocument = translationDocument;
//    }
//
//    public NationalityVM getDriverNationality() {
//        return driverNationality;
//    }
//
//    public void setDriverNationality(NationalityVM driverNationality) {
//        this.driverNationality = driverNationality;
//    }
//
//    public List<ForeignLicenseCategoryDetailsJsonType> getForeignLicenseCategoryDetails() {
//        return foreignLicenseCategoryDetails;
//    }
//
//    public void setForeignLicenseCategoryDetails(List<ForeignLicenseCategoryDetailsJsonType> foreignLicenseCategoryDetails) {
//        this.foreignLicenseCategoryDetails = foreignLicenseCategoryDetails;
//    }
//
//    @Override
//    public String toString() {
//        return "ForeignLicenseDetailVM{" +
//            ", licenseNo=" + getLicenseNo() +
//            ", templateId=" + getTemplateId() +
//            ", issueDate=" + getIssueDate() +
//            ", expiryDate=" + getExpiryDate() +
//            ", requestDate=" + getRequestDate() +
//            ", issuedFromCountryDetails=" + getIssuedFromCountryDetails() +
//            ", validity=" + getValidity() +
//            ", foreignLicenseCategoryDetails=" + getForeignLicenseCategoryDetails() +
//            '}';
//    }
//}
