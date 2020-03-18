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
import com.isoft.dls.domain.enumeration.common.SystemType;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "Verify Otp Corporate Info Request VM. @author Mena Emiel.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OtpCorporateVerificationRequestVM implements ViewModel {

    @NotNull
    @JsonProperty("tradeLicenseNo")
    private String tradeLicenseNo;

    @NotNull
    @JsonProperty("agentName")
    private String agentName;

    @NotNull
    @JsonProperty("authority")
    private String authority;

    @JsonProperty("systemType")
    private SystemType systemType;

    @NotNull
    @Pattern(regexp = DataFormatterUtil.MOBILE_NUMBER_PATTERN)
    @Size(min = 12, max = 12)
    @JsonProperty("mobileNo")
    private String mobileNo;

    @NotNull
    @Size(min = 6, max = 6)
//    @Pattern(regexp = DataFormatterUtil.OTP_PATTERN)
    @JsonProperty("token")
    private String token;

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "OtpCorporateVerificationRequestVM{" +
            "tradeLicenseNo='" + tradeLicenseNo + '\'' +
            ", agentName='" + agentName + '\'' +
            ", authority='" + authority + '\'' +
            ", systemType=" + systemType +
            ", mobileNo='" + mobileNo + '\'' +
            ", token='" + token + '\'' +
            '}';
    }
}
