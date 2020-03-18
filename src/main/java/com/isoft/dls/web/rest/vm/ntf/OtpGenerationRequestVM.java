/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 3:04 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.ntf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.common.util.DataFormatterUtil;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.enumeration.common.Language;
import com.isoft.dls.domain.enumeration.common.SystemType;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.*;

/**
 * View Model object for storing the required information for Otp Generation.
 *
 * @author Rami Nassar
 */
@ApiModel(description = "Otp Generation VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OtpGenerationRequestVM implements ViewModel {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("phaseType")
    private PhaseType phaseType;

    @JsonProperty("systemType")
    private SystemType systemType;

    @NotBlank
    @Pattern(regexp = DataFormatterUtil.MOBILE_NUMBER_PATTERN)
    @Size(min = 12, max = 12)
    @JsonProperty("mobileNo")
    private String mobileNo;

    @Email
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("language")
    private Language language;


    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
