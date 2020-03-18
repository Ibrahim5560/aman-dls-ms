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
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.enumeration.common.Gender;
import com.isoft.dls.domain.type.MultilingualJsonType;
import io.swagger.annotations.ApiModel;

/**
 * A VM for the customer application summary application entity.
 */
@ApiModel(description = "Application customer info VM entity. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationCustomerInfoVM {

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("genderDescription")
    private MultilingualJsonType genderDescription;

    @JsonProperty("englishSponsorName")
    private String englishSponsorName;

    @JsonProperty("arabicSponsorName")
    private String arabicSponsorName;

    @JsonProperty("professionCode")
    private String professionCode;

    @JsonProperty("professionDescription")
    private MultilingualJsonType professionDescription;

    @JsonProperty("email")
    private String email;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MultilingualJsonType getGenderDescription() {
        return genderDescription;
    }

    public void setGenderDescription(MultilingualJsonType genderDescription) {
        this.genderDescription = genderDescription;
    }

    public String getEnglishSponsorName() {
        return StringUtil.formatString(englishSponsorName);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ApplicationCustomerInfoVM{" +
            "gender=" + gender +
            ", genderDescription=" + genderDescription +
            ", englishSponsorName='" + englishSponsorName + '\'' +
            ", arabicSponsorName='" + arabicSponsorName + '\'' +
            ", professionCode='" + professionCode + '\'' +
            ", professionDescription=" + professionDescription +
            ", email=" + email +
            '}';
    }
}
