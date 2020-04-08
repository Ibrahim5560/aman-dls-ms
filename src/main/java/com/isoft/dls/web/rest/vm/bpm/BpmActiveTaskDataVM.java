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

import java.util.List;

/**
 * A VM of Bpm Task Details API.
 */
@ApiModel(description = "Bpm user inbox VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BpmActiveTaskDataVM {

    @JsonProperty("tasks")
    List<BpmActiveTaskVM> tasks;

    public List<BpmActiveTaskVM> getTasks() {
        return tasks;
    }

    public void setTasks(List<BpmActiveTaskVM> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "BpmActiveTaskDataVM{" +
            "tasks=" + tasks +
            '}';
    }
}
