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
import com.isoft.dls.domain.enumeration.common.Gender;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * A VM of Customer Summary.
 */
@ApiModel(description = "Customer Summary VM. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerInfoSummaryVM implements ViewModel {

    @JsonProperty("trafficCodeNo")
    private Long trafficCodeNo;

    @JsonProperty("emiratesId")
    private  String emiratesId;

    @JsonProperty("arabicFullName")
    private String arabicFullName;

    @JsonProperty("englishFullName")
    private String englishFullName;

    @JsonProperty("birthdate")
    private LocalDate birthdate;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("nationality")
    private NationalityVM nationality;

    public CustomerInfoSummaryVM() {
        //Default Constructor
    }

    public Long getTrafficCodeNo() {
        return trafficCodeNo;
    }

    public void setTrafficCodeNo(Long trafficCodeNo) {
        this.trafficCodeNo = trafficCodeNo;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public String getArabicFullName() {
        return arabicFullName;
    }

    public void setArabicFullName(String arabicFullName) {
        this.arabicFullName = arabicFullName;
    }

    public String getEnglishFullName() {
        return englishFullName;
    }

    public void setEnglishFullName(String englishFullName) {
        this.englishFullName = englishFullName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public NationalityVM getNationality() {
        return nationality;
    }

    public void setNationality(NationalityVM nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "CustomerInfoSummaryVM{" +
            ", trafficCodeNo=" + getTrafficCodeNo() +
            ", name=" + getArabicFullName() +
            ", englishFullName=" + getEnglishFullName() +
            ", emiratesId=" + getEmiratesId() +
            ", gender=" + getGender() +
            ", photo=" + getPhoto() +
            ", nationality=" + getNationality() +
            "}";
    }
}
