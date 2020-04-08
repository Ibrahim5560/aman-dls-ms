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
import com.isoft.dls.common.util.DataFormatterUtil;
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.enumeration.trn.PhaseType;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.web.rest.vm.ntf.OtpGenerationResponseVM;
import com.isoft.dls.web.rest.vm.util.ViewModel;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * A VM of Customer Identity
 */
@ApiModel(description = "customer identity . @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerIdentityVM implements ViewModel {

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("customerNo")
    private Long customerNo;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("unifiedNo")
    private String unifiedNo;

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("arabicCustomerName")
    private String arabicCustomerName;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("firstTwoDigits")
    private String firstTwoDigits;

    @JsonProperty("remainingNameLength")
    private String customerNameLength;

    @JsonProperty("otpTokenDetails")
    private OtpGenerationResponseVM otpTokenDetails;

    @JsonProperty("email")
    private String email;

    @JsonProperty("eidNumber")
    @Pattern(regexp = DataFormatterUtil.NUMBER_PATTERN)
    private String emiratesIdNo;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public String getUnifiedNo() {
        return unifiedNo;
    }

    public void setUnifiedNo(String unifiedNo) {
        this.unifiedNo = unifiedNo;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getArabicCustomerName() {
        return arabicCustomerName;
    }

    public void setArabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    public String getEnglishCustomerName() {
        return englishCustomerName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public String getEmiratesIdNo() {
        return emiratesIdNo;
    }

    public void setEmiratesIdNo(String emiratesIdNo) {
        this.emiratesIdNo = emiratesIdNo;
    }

    @Override
    public String toString() {
        return "CustomerIdentityVM{" +
            "applicationId=" + applicationId +
            ", customerNo=" + customerNo +
            ", applicationCriteria=" + applicationCriteria +
            ", unifiedNo='" + unifiedNo + '\'' +
            ", activePhase=" + activePhase +
            ", mobileNo='" + mobileNo + '\'' +
            ", arabicCustomerName='" + arabicCustomerName + '\'' +
            ", englishCustomerName='" + englishCustomerName + '\'' +
            ", firstTwoDigits='" + firstTwoDigits + '\'' +
            ", customerNameLength='" + customerNameLength + '\'' +
            ", otpTokenDetails=" + otpTokenDetails +
            ", email='" + email + '\'' +
            ", emiratesIdNo='" + emiratesIdNo + '\'' +
            '}';
    }
}
