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

import com.isoft.dls.common.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "load profile response . @author Sherif Thabet")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoadProfileSimilarityVM {

    @JsonProperty("remainingNameLength")
    private String customerNameLength;

    @JsonProperty("firstTwoDigits")
    private String firstTwoDigits;

    @JsonProperty("userProfileNo")
    private Long userProfileNo;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    public String getCustomerNameLength() {
        if(!StringUtil.isBlank(this.englishCustomerName)) {
            customerNameLength = ""+englishCustomerName.substring(2).length();
        }
        return customerNameLength;
    }

    public void setCustomerNameLength(String customerNameLength) {
        this.customerNameLength = customerNameLength;
    }

    public String getFirstTwoDigits() {
        if(!StringUtil.isBlank(this.englishCustomerName)) {
            firstTwoDigits = englishCustomerName.substring(0, 2);
        }
        return firstTwoDigits;
    }

    public void setFirstTwoDigits(String firstTwoDigits) {
        this.firstTwoDigits = firstTwoDigits;
    }

    public Long getUserProfileNo() {
        return userProfileNo;
    }

    public void setUserProfileNo(Long userProfileNo) {
        this.userProfileNo = userProfileNo;
    }

    public String getEnglishCustomerName() {
        return null;
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    @Override
    public String toString() {
        return "LoadProfileSimilarityVM{" +
            "customerNameLength='" + customerNameLength + '\'' +
            ", firstTwoDigits='" + firstTwoDigits + '\'' +
            ", userProfileNo=" + userProfileNo +
            ", englishCustomerName='" + englishCustomerName + '\'' +
            '}';
    }
}
