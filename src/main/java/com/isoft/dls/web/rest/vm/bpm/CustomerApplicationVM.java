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

import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * A VM of Create Traffic File Service Request API.
 */
@ApiModel(description = "Payment Request Response VM. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerApplicationVM {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("userId")
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "CustomerApplicationVM{" +
            "applicationId=" + applicationId +
            ", applicationCriteria=" + applicationCriteria +
            ", userId=" + userId +
            '}';
    }
}
