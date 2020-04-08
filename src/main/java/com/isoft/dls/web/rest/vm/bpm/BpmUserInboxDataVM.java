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
public class BpmUserInboxDataVM {

    @JsonProperty("instanceId")
    private Long instanceId;

    @JsonProperty("taskAssignedTo")
    private BpmUserInboxAssigneeInfoVM taskAssignedTo;

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("instanceCreateDate")
    private LocalDateTime instanceCreateDate;

    @JsonProperty("data")
    private LocalDateTime instanceDueDate;

    @JsonProperty("instanceModifyDate")
    private LocalDateTime instanceModifyDate;

    @JsonProperty("taskDueDate")
    private LocalDateTime taskDueDate;

    @JsonProperty("taskStatus")
    private String taskStatus;

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getInstanceCreateDate() {
        return instanceCreateDate;
    }

    public void setInstanceCreateDate(LocalDateTime instanceCreateDate) {
        this.instanceCreateDate = instanceCreateDate;
    }

    public LocalDateTime getInstanceDueDate() {
        return instanceDueDate;
    }

    public void setInstanceDueDate(LocalDateTime instanceDueDate) {
        this.instanceDueDate = instanceDueDate;
    }

    public LocalDateTime getInstanceModifyDate() {
        return instanceModifyDate;
    }

    public void setInstanceModifyDate(LocalDateTime instanceModifyDate) {
        this.instanceModifyDate = instanceModifyDate;
    }

    public LocalDateTime getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(LocalDateTime taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public BpmUserInboxAssigneeInfoVM getTaskAssignedTo() {
        return taskAssignedTo;
    }

    public void setTaskAssignedTo(BpmUserInboxAssigneeInfoVM taskAssignedTo) {
        this.taskAssignedTo = taskAssignedTo;
    }

    @Override
    public String toString() {
        return "BpmUserInboxDataVM{" +
                "instanceId=" + instanceId +
                ", taskAssignedTo=" + taskAssignedTo +
                ", taskId=" + taskId +
                ", instanceCreateDate=" + instanceCreateDate +
                ", instanceDueDate=" + instanceDueDate +
                ", instanceModifyDate=" + instanceModifyDate +
                ", taskDueDate=" + taskDueDate +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
