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
import com.isoft.dls.domain.enumeration.trn.PhaseType;
import com.isoft.dls.web.rest.vm.ntf.OtpGenerationResponseVM;
import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * A VM of Load Profile response
 */
@ApiModel(description = "load profile response . @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoadProfileResponseVM implements ViewModel {

    @JsonProperty("userProfileNo")
    private Long userProfileNo;

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("trafficCodeNo")
    private Long trafficCodeNo;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("firstTwoDigits")
    private String firstTwoDigits;

    @JsonProperty("remainingNameLength")
    private String customerNameLength;

    @JsonProperty("otpTokenDetails")
    private OtpGenerationResponseVM otpTokenDetails;

    @JsonProperty("loadProfileSimilarityVM")
    private List<LoadProfileSimilarityVM> loadProfileSimilarityVM;

    public Long getUserProfileNo() {
        return userProfileNo;
    }

    public void setUserProfileNo(Long userProfileNo) {
        this.userProfileNo = userProfileNo;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEnglishCustomerName() {
        return null;
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
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

    public String getCustomerNameLength() {
        if(!StringUtil.isBlank(this.englishCustomerName)) {
            customerNameLength = ""+englishCustomerName.substring(2).length();
        }
        return customerNameLength;
    }

    public void setCustomerNameLength(String customerNameLength) {
        this.customerNameLength = customerNameLength;
    }

    public OtpGenerationResponseVM getOtpTokenDetails() {
        return otpTokenDetails;
    }

    public void setOtpTokenDetails(OtpGenerationResponseVM otpTokenDetails) {
        this.otpTokenDetails = otpTokenDetails;
    }

    public Long getTrafficCodeNo() {
        return trafficCodeNo;
    }

    public void setTrafficCodeNo(Long trafficCodeNo) {
        this.trafficCodeNo = trafficCodeNo;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public List<LoadProfileSimilarityVM> getLoadProfileSimilarityVM() {
        return loadProfileSimilarityVM;
    }

    public void setLoadProfileSimilarityVM(List<LoadProfileSimilarityVM> loadProfileSimilarityVM) {
        this.loadProfileSimilarityVM = loadProfileSimilarityVM;
    }


    @Override
    public String toString() {
        return "LoadProfileResponseVM{" +
            "userProfileNo=" + userProfileNo +
            ", applicationId=" + applicationId +
            ", trafficCodeNo=" + trafficCodeNo +
            ", mobileNo='" + mobileNo + '\'' +
            ", activePhase=" + activePhase +
            ", englishCustomerName='" + englishCustomerName + '\'' +
            ", firstTwoDigits='" + firstTwoDigits + '\'' +
            ", customerNameLength='" + customerNameLength + '\'' +
            ", otpTokenDetails=" + otpTokenDetails +
            ", loadProfileSimilarityVM=" + loadProfileSimilarityVM +
            '}';
    }
}
