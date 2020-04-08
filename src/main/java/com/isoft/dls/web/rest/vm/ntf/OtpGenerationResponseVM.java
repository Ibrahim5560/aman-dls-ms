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
import com.isoft.dls.domain.enumeration.common.OtpInformationStatus;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * View Model object for storing the required information for Otp Generation.
 *
 * @author Ahmad Abo AlShamat
 */
@ApiModel(description = "Generate Otp Response VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OtpGenerationResponseVM implements ViewModel {

    @NotNull
    @JsonProperty("tokenExpiryTime")
    private LocalDateTime tokenDue;

    @NotNull
    @JsonProperty("status")
    private OtpInformationStatus status;

    public LocalDateTime getTokenDue() {
        return tokenDue;
    }

    public void setTokenDue(LocalDateTime tokenDue) {
        this.tokenDue = tokenDue;
    }

    public OtpInformationStatus getStatus() {
        return status;
    }

    public void setStatus(OtpInformationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OtpGenerationResponseVM{" +
                "tokenDue=" + tokenDue +
                ", status=" + status +
                '}';
    }
}
