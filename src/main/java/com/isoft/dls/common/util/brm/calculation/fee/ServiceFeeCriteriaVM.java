/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:11 PM  - File created.
 */

package com.isoft.dls.common.util.brm.calculation.fee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Service Fee Criteria VM
 *
 * @author Tariq Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceFeeCriteriaVM implements Serializable {

    /*
     * Accessors
     */
    @JsonProperty("beneficiaryCode")
    private String beneficiaryCode;

    @JsonProperty("feeCode")
    private String feeCode;

    @JsonProperty("licenseCategoryCode")
    private String licenseCategoryCode;

    /*
     * Setters & Getters
     */

    public String getBeneficiaryCode() {
        return beneficiaryCode;
    }

    public void setBeneficiaryCode(String beneficiaryCode) {
        this.beneficiaryCode = beneficiaryCode;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public String getLicenseCategoryCode() {
        return licenseCategoryCode;
    }

    public void setLicenseCategoryCode(String licenseCategoryCode) {
        this.licenseCategoryCode = licenseCategoryCode;
    }

    @Override
    public String toString() {
        return "ServiceFeeCriteriaVM{" +
            "beneficiaryCode='" + beneficiaryCode + '\'' +
            ", feeCode='" + feeCode + '\'' +
            ", licenseCategoryCode='" + licenseCategoryCode + '\'' +
            '}';
    }
}
