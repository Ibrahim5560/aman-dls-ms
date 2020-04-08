/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:24 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.bpm;

import com.isoft.dls.domain.enumeration.common.Language;
import com.isoft.dls.domain.type.MultilingualJsonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * A VM of Create Traffic File Service Request API.
 */
@ApiModel(description = "Payment Request Response VM. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TrafficFileServiceRequestVM {

    @JsonProperty("handicapped")
    private Boolean handicapped;

    @JsonProperty("vip")
    private Boolean vip;

    @JsonProperty("smsNotification")
    private Boolean smsNotification;

    @JsonProperty("emailNotification")
    private Boolean emailNotification;

    @JsonProperty("pushNotification")
    private Boolean pushNotification;

    @JsonProperty("preferredLanguage")
    private Language preferredLanguage;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("passportNo")
    private String passportNo;

    @JsonProperty("passportExpiryDate")
    private LocalDate passportExpiryDate;

    @JsonProperty("immigrationFileType")
    private String immigrationFileType;

    @JsonProperty("immigrationFileTypeDescription")
    private MultilingualJsonType immigrationFileTypeDescription;

    @JsonProperty("residencyNo")
    private String residencyNo;

    @JsonProperty("residencyExpiryDate")
    private LocalDate residencyExpiryDate;

    @JsonProperty("sponsorTypeCode")
    private String sponsorTypeCode;

    @JsonProperty("sponsorTypeDescription")
    private MultilingualJsonType sponsorTypeDescription;

    @JsonProperty("englishSponsorName")
    private String englishSponsorName;

    @JsonProperty("arabicSponsorName")
    private String arabicSponsorName;

    @JsonProperty("unifiedNo")
    private String unifiedNo;

    @JsonProperty("eidNumber")
    private String eidNumber;

    @JsonProperty("eidExpiryDate")
    private LocalDate eidExpiryDate;

    @JsonProperty("englishFullName")
    private String englishFullName;

    @JsonProperty("arabicFullName")
    private String arabicFullName;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("email")
    private String email;

    @JsonProperty("genderCode")
    private String genderCode;

    @JsonProperty("genderDescription")
    private MultilingualJsonType genderDescription;

    @JsonProperty("immigrationFileEmirateCode")
    private String immigrationFileEmirateCode;

    @JsonProperty("immigrationFileEmirateDescription")
    private MultilingualJsonType immigrationFileEmirateDescription;

    @JsonProperty("professionCode")
    private String professionCode;

    @JsonProperty("professionDescription")
    private MultilingualJsonType professionDescription;

    @JsonProperty("nationalityCode")
    private String nationalityCode;

    @JsonProperty("nationalityDescription")
    private MultilingualJsonType nationalityDescription;

    @JsonProperty("customerPhoto")
    private String customerPhoto;

    @JsonProperty("customerPhotoMimeType")
    private String customerPhotoMimeType;

    public Boolean getHandicapped() {
        return handicapped;
    }

    public void setHandicapped(Boolean handicapped) {
        this.handicapped = handicapped;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Boolean getSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(Boolean smsNotification) {
        this.smsNotification = smsNotification;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
    }

    public Boolean getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Language preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public LocalDate getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public void setPassportExpiryDate(LocalDate passportExpiryDate) {
        this.passportExpiryDate = passportExpiryDate;
    }

    public String getImmigrationFileType() {
        return immigrationFileType;
    }

    public void setImmigrationFileType(String immigrationFileType) {
        this.immigrationFileType = immigrationFileType;
    }

    public MultilingualJsonType getImmigrationFileTypeDescription() {
        return immigrationFileTypeDescription;
    }

    public void setImmigrationFileTypeDescription(MultilingualJsonType immigrationFileTypeDescription) {
        this.immigrationFileTypeDescription = immigrationFileTypeDescription;
    }

    public String getResidencyNo() {
        return residencyNo;
    }

    public void setResidencyNo(String residencyNo) {
        this.residencyNo = residencyNo;
    }

    public LocalDate getResidencyExpiryDate() {
        return residencyExpiryDate;
    }

    public void setResidencyExpiryDate(LocalDate residencyExpiryDate) {
        this.residencyExpiryDate = residencyExpiryDate;
    }

    public String getSponsorTypeCode() {
        return sponsorTypeCode;
    }

    public void setSponsorTypeCode(String sponsorTypeCode) {
        this.sponsorTypeCode = sponsorTypeCode;
    }

    public MultilingualJsonType getSponsorTypeDescription() {
        return sponsorTypeDescription;
    }

    public void setSponsorTypeDescription(MultilingualJsonType sponsorTypeDescription) {
        this.sponsorTypeDescription = sponsorTypeDescription;
    }

    public String getEnglishSponsorName() {
        return englishSponsorName;
    }

    public void setEnglishSponsorName(String englishSponsorName) {
        this.englishSponsorName = englishSponsorName;
    }

    public String getArabicSponsorName() {
        return arabicSponsorName;
    }

    public void setArabicSponsorName(String arabicSponsorName) {
        this.arabicSponsorName = arabicSponsorName;
    }

    public String getUnifiedNo() {
        return unifiedNo;
    }

    public void setUnifiedNo(String unifiedNo) {
        this.unifiedNo = unifiedNo;
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

    public String getImmigrationFileEmirateCode() {
        return immigrationFileEmirateCode;
    }

    public void setImmigrationFileEmirateCode(String immigrationFileEmirateCode) {
        this.immigrationFileEmirateCode = immigrationFileEmirateCode;
    }

    public MultilingualJsonType getImmigrationFileEmirateDescription() {
        return immigrationFileEmirateDescription;
    }

    public void setImmigrationFileEmirateDescription(MultilingualJsonType immigrationFileEmirateDescription) {
        this.immigrationFileEmirateDescription = immigrationFileEmirateDescription;
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
    }

    public MultilingualJsonType getProfessionDescription() {
        return professionDescription;
    }

    public void setProfessionDescription(MultilingualJsonType professionDescription) {
        this.professionDescription = professionDescription;
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

    public String getCustomerPhoto() {
        return customerPhoto;
    }

    public void setCustomerPhoto(String customerPhoto) {
        this.customerPhoto = customerPhoto;
    }

    public String getCustomerPhotoMimeType() {
        return customerPhotoMimeType;
    }

    public void setCustomerPhotoMimeType(String customerPhotoMimeType) {
        this.customerPhotoMimeType = customerPhotoMimeType;
    }


    @Override
    public String toString() {
        return "TrafficFileServiceRequestVM{" +
            ", handicapped=" + handicapped +
            ", vip=" + vip +
            ", smsNotification=" + smsNotification +
            ", emailNotification=" + emailNotification +
            ", pushNotification=" + pushNotification +
            ", preferredLanguage='" + preferredLanguage + '\'' +
            ", birthdate=" + birthdate +
            ", passportNo='" + passportNo + '\'' +
            ", passportExpiryDate=" + passportExpiryDate +
            ", immigrationFileType='" + immigrationFileType + '\'' +
            ", immigrationFileTypeDescription=" + immigrationFileTypeDescription +
            ", residencyNo='" + residencyNo + '\'' +
            ", residencyExpiryDate=" + residencyExpiryDate +
            ", sponsorTypeCode='" + sponsorTypeCode + '\'' +
            ", sponsorTypeDescription=" + sponsorTypeDescription +
            ", englishSponsorName='" + englishSponsorName + '\'' +
            ", arabicSponsorName='" + arabicSponsorName + '\'' +
            ", unifiedNo='" + unifiedNo + '\'' +
            ", eidNumber='" + eidNumber + '\'' +
            ", eidExpiryDate=" + eidExpiryDate +
            ", englishFullName='" + englishFullName + '\'' +
            ", arabicFullName='" + arabicFullName + '\'' +
            ", mobileNo='" + mobileNo + '\'' +
            ", email='" + email + '\'' +
            ", genderCode='" + genderCode + '\'' +
            ", genderDescription=" + genderDescription +
            ", immigrationFileEmirateCode='" + immigrationFileEmirateCode + '\'' +
            ", immigrationFileEmirateDescription=" + immigrationFileEmirateDescription +
            ", professionCode='" + professionCode + '\'' +
            ", professionDescription=" + professionDescription +
            ", nationalityCode='" + nationalityCode + '\'' +
            ", nationalityDescription=" + nationalityDescription +
            ", customerPhoto='" + customerPhoto + '\'' +
            ", customerPhotoMimeType='" + customerPhotoMimeType + '\'' +
            '}';
    }
}
