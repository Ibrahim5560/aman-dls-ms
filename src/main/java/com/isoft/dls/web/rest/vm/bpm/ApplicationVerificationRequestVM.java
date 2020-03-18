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

import com.isoft.dls.domain.enumeration.trn.DrivingSchoolReviewStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * A VM of Learning Files Initiation API.
 */
@ApiModel(description = "Learning File Initiation Request VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationVerificationRequestVM {

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("drivingSchoolReviewStatus")
    private DrivingSchoolReviewStatus drivingSchoolReviewStatus;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }


    public DrivingSchoolReviewStatus getDrivingSchoolReviewStatus() {
        return drivingSchoolReviewStatus;
    }

    public void setDrivingSchoolReviewStatus(DrivingSchoolReviewStatus drivingSchoolReviewStatus) {
        this.drivingSchoolReviewStatus = drivingSchoolReviewStatus;
    }

    @Override
    public String toString() {
        return "ApplicationVerificationRequestVM{" +
            "taskId=" + taskId +
            ", drivingSchoolReviewStatus=" + drivingSchoolReviewStatus +
            '}';
    }
}
