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
 * A VM of Contract Acceptance API.
 */
@ApiModel(description = "Contract Acceptance VM. @author Tariq Abu Amireh.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ContractAcceptanceVM {

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("acceptContract")
    private boolean acceptContract;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public boolean isAcceptContract() {
        return acceptContract;
    }

    public void setAcceptContract(boolean acceptContract) {
        this.acceptContract = acceptContract;
    }

    @Override
    public String toString() {
        return "ContractAcceptanceVM{" +
            "taskId=" + taskId +
            ", acceptContract=" + acceptContract +
            '}';
    }
}
