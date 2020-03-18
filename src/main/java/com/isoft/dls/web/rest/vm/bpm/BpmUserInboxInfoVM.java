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
import com.isoft.dls.web.rest.vm.trn.ProcessInstanceApplicationVM;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * A VM of Bpm Task Details API.
 */
@ApiModel(description = "Bpm user inbox VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BpmUserInboxInfoVM {

    @JsonProperty("requestedSize")
    private Integer requestedSize;

    @JsonProperty("retrivedSize")
    private Integer retrivedSize;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("applications")
    private List<ProcessInstanceApplicationVM> applications;

    public Integer getRequestedSize() {
        return requestedSize;
    }

    public void setRequestedSize(Integer requestedSize) {
        this.requestedSize = requestedSize;
    }

    public Integer getRetrivedSize() {
        return retrivedSize;
    }

    public void setRetrivedSize(Integer retrivedSize) {
        this.retrivedSize = retrivedSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<ProcessInstanceApplicationVM> getApplications() {
        return applications;
    }

    public void setApplications(List<ProcessInstanceApplicationVM> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "BpmUserInboxInfoVM{" +
                "requestedSize=" + requestedSize +
                ", retrivedSize=" + retrivedSize +
                ", offset=" + offset +
                ", totalCount=" + totalCount +
                ", applications=" + applications +
                '}';
    }
}
