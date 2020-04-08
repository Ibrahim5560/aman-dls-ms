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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customer Info DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerInfoVM {

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

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("paymentReference")
    private Long paymentReference;

    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

//    public CustomerInfoVM(CustomerDTO customerDTO) {
//        this.setTrafficCodeNo(customerDTO.getTrafficCodeNo());
//        this.setArabicFullName(customerDTO.getUserProfile().getArabicFullName());
//        this.setEnglishFullName(customerDTO.getUserProfile().getEnglishFullName());
//        this.setTrafficCodeNo(customerDTO.getTrafficCodeNo());
//        this.setEmiratesId(customerDTO.getEidNumber());
//        this.setBirthdate(customerDTO.getBirthdate());
//        this.setGender(customerDTO.getUserProfile().getGender());
//        this.setPhoto(customerDTO.getUserProfile().getPhoto());
//        this.setNationality(new NationalityVM());
//        this.getNationality().setCode(customerDTO.getUserProfile().getNationality().getCode());
//        this.getNationality().setName(customerDTO.getUserProfile().getNationality().getName());
//    }

    public CustomerInfoVM() {
        // Default Constructor
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "CustomerInfoVM{" +
            "trafficCodeNo=" + trafficCodeNo +
            ", emiratesId='" + emiratesId + '\'' +
            ", arabicFullName='" + arabicFullName + '\'' +
            ", englishFullName='" + englishFullName + '\'' +
            ", birthdate=" + birthdate +
            ", gender=" + gender +
            ", photo='" + photo + '\'' +
            ", nationality=" + nationality +
            ", paymentMethod='" + paymentMethod + '\'' +
            ", paymentReference=" + paymentReference +
            ", paymentDate=" + paymentDate +
            '}';
    }
}
