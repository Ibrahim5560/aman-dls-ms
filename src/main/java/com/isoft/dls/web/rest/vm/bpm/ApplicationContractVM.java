/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:24 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.bpm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import io.swagger.annotations.ApiModel;

/**
 * A VM of Create Payment Request API.
 */
@ApiModel(description = "Payment Request Response VM. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationContractVM {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

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

    @Override
    public String toString() {
        return "ApplicationContractVM{" +
                "applicationId=" + applicationId +
                ", applicationCriteria=" + applicationCriteria +
                '}';
    }
}
