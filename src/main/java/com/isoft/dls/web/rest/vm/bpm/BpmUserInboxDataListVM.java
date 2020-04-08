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
public class BpmUserInboxDataListVM {

    @JsonProperty("data")
    private List<BpmUserInboxDataVM> dataList;

    @JsonProperty("status")
    private String status;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("requestedSize")
    private Integer requestedSize;

    @JsonProperty("totalCount")
    private Integer totalCount;

    public List<BpmUserInboxDataVM> getDataList() {
        return dataList;
    }

    public void setDataList(List<BpmUserInboxDataVM> dataList) {
        this.dataList = dataList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getRequestedSize() {
        return requestedSize;
    }

    public void setRequestedSize(Integer requestedSize) {
        this.requestedSize = requestedSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "BpmUserInboxDataListVM{" +
                "dataList=" + dataList +
                ", status='" + status + '\'' +
                ", offset=" + offset +
                ", size=" + size +
                ", requestedSize=" + requestedSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
