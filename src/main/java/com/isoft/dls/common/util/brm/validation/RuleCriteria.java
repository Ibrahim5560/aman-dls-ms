/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:12 PM  - File created.
 */

package com.isoft.dls.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuleCriteria implements Serializable {

    /*
     * Instance Variables
     */
    @JsonProperty("applicantAge")
    private Integer applicantAge;

    @JsonProperty("licenseCategoryCode")
    private String licenseCategoryCode;

    /*
     * Default Constructor
     */
    public RuleCriteria() {
        // Default Constructor
    }

    /*
     * Setters & Getters
     */

    public Integer getApplicantAge() {
        return applicantAge;
    }

    public void setApplicantAge(Integer applicantAge) {
        this.applicantAge = applicantAge;
    }

    public String getLicenseCategoryCode() {
        return licenseCategoryCode;
    }

    public void setLicenseCategoryCode(String licenseCategoryCode) {
        this.licenseCategoryCode = licenseCategoryCode;
    }

    @Override
    public String toString() {
        return "RuleCriteria{" +
            "applicantAge=" + applicantAge +
            ", licenseCategoryCode='" + licenseCategoryCode + '\'' +
            '}';
    }
}
