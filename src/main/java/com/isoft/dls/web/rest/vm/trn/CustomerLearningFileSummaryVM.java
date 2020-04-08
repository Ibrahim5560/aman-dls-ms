/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.trn;

import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * A VM of Customer Learning File Summary API.
 */
@ApiModel(description = "Learning File Info VM. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerLearningFileSummaryVM implements ViewModel {

    @JsonProperty("customerInfo")
    private CustomerInfoSummaryVM customerInfoSummaryVM;

    @JsonProperty("learningFileDetails")
    private LearningFileDetailSummaryVM learningFileDetailSummaryVM;

    public CustomerInfoSummaryVM getCustomerInfoSummaryVM() {
        return customerInfoSummaryVM;
    }

    public void setCustomerInfoSummaryVM(CustomerInfoSummaryVM customerInfoSummaryVM) {
        this.customerInfoSummaryVM = customerInfoSummaryVM;
    }

    public LearningFileDetailSummaryVM getLearningFileDetailSummaryVM() {
        return learningFileDetailSummaryVM;
    }

    public void setLearningFileDetailSummaryVM(LearningFileDetailSummaryVM learningFileDetailSummaryVM) {
        this.learningFileDetailSummaryVM = learningFileDetailSummaryVM;
    }

    @Override
    public String toString() {
        return "CustomerLearningFileSummaryVM {" +
            "customerInfoSummaryVM=" + getCustomerInfoSummaryVM() +
            ", learningFileDetailSummaryVM=" + getLearningFileDetailSummaryVM() +
            '}';
    }
}
