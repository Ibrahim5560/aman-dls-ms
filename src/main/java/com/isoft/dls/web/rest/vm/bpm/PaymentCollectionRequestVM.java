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
 * A VM of Payment Clearance Request API.
 */
@ApiModel(description = "Payment Clearance Request VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentCollectionRequestVM {

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("paymentCollected")
    private boolean paymentCollected;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public boolean isPaymentCollected() {
        return paymentCollected;
    }

    public void setPaymentCollected(boolean paymentCollected) {
        this.paymentCollected = paymentCollected;
    }

    @Override
    public String toString() {
        return "PaymentCollectionRequestVM{" +
                "taskId=" + taskId +
                ", paymentCollected=" + paymentCollected +
                '}';
    }
}
