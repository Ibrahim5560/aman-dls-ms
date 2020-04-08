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
 * A VM of Bpm Task Details API.
 */
@ApiModel(description = "Bpm Task Details VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BpmTaskDetailsVM {

    @JsonProperty("data")
    private TaskDetailsDataVM taskDetailsDataVM;

    @JsonProperty("status")
    private String status;

    public TaskDetailsDataVM getTaskDetailsDataVM() {
        return taskDetailsDataVM;
    }

    public void setTaskDetailsDataVM(TaskDetailsDataVM taskDetailsDataVM) {
        this.taskDetailsDataVM = taskDetailsDataVM;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BpmTaskDetailsVM{" +
                "taskDetailsDataVM=" + taskDetailsDataVM +
                ", status='" + status + '\'' +
                '}';
    }
}
