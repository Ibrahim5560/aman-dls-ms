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
import io.swagger.annotations.ApiModel;

/**
 * A VM of Learning Files Initiation API.
 */
@ApiModel(description = "Learning File Initiation Response VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LearningFileInitiationResponseVM {

    @JsonProperty("applicationReferenceNo")
    private Long applicationReferenceNo;

    @JsonProperty("status")
    private Integer status;

    public Long getApplicationReferenceNo() {
        return applicationReferenceNo;
    }

    public void setApplicationReferenceNo(Long applicationReferenceNo) {
        this.applicationReferenceNo = applicationReferenceNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LearningFileInitiationResponseVM{" +
                "applicationReferenceNo=" + applicationReferenceNo +
                ", status=" + status +
                '}';
    }
}
