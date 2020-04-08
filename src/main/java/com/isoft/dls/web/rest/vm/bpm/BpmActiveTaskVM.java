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

import java.time.LocalDateTime;

/**
 * A VM of Bpm Task Details API.
 */
@ApiModel(description = "Bpm user inbox VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BpmActiveTaskVM {

    @JsonProperty("tkiid")
    private Long taskId;

    private String name;

    private String status;

    private String owner;

    private String assignedTo;

    private String assignedToDisplayName;

    private String assignedToType;

    private LocalDateTime dueTime;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignedToDisplayName() {
        return assignedToDisplayName;
    }

    public void setAssignedToDisplayName(String assignedToDisplayName) {
        this.assignedToDisplayName = assignedToDisplayName;
    }

    public String getAssignedToType() {
        return assignedToType;
    }

    public void setAssignedToType(String assignedToType) {
        this.assignedToType = assignedToType;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }

    @Override
    public String toString() {
        return "BpmActiveTaskVM{" +
            "taskId=" + taskId +
            ", name='" + name + '\'' +
            ", status='" + status + '\'' +
            ", owner='" + owner + '\'' +
            ", assignedTo='" + assignedTo + '\'' +
            ", assignedToDisplayName='" + assignedToDisplayName + '\'' +
            ", assignedToType='" + assignedToType + '\'' +
            ", dueTime=" + dueTime +
            '}';
    }
}
