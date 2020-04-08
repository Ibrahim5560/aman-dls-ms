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

import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * A VM of Task Details Data.
 */
@ApiModel(description = "Task Details Data VM. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskDetailsDataVM implements ViewModel {

    @JsonProperty("piid")
    private Long processInstanceId;

    @JsonProperty("tkiid")
    private Long taskId;

    @JsonProperty("assignedToType")
    private String assignedToType;

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAssignedToType() {
        return assignedToType;
    }

    public void setAssignedToType(String assignedToType) {
        this.assignedToType = assignedToType;
    }

    @Override
    public String toString() {
        return "BpmTaskDetailsVM{" +
            "processInstanceId=" + processInstanceId +
            ", taskId='" + taskId + '\'' +
            ", assignedToType='" + assignedToType + '\'' +
            '}';
    }
}
