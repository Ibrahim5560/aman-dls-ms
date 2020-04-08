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
@ApiModel(description = "Bpm active task data VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BpmActiveTaskInfoVM {

    @JsonProperty("data")
    private BpmActiveTaskDataVM data;

    public BpmActiveTaskDataVM getData() {
        return data;
    }

    public void setData(BpmActiveTaskDataVM data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BpmActiveTaskInfoVM{" +
            "data=" + data +
            '}';
    }
}
