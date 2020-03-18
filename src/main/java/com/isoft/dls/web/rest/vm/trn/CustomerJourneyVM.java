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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

/**
 * A VM for customer journey application entity.
 */
@ApiModel(description = "customer journey application (trn_application) entity. @author Tariq Abu Amireh.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerJourneyVM implements ViewModel {

    @JsonProperty("applicationDetails")
    private CustomerApplicationSummaryVM customerApplicationSummaryVM;

    @JsonProperty("productDocument")
    private CustomerLearningFileSummaryVM customerLearningFileSummaryVM;

    public CustomerApplicationSummaryVM getCustomerApplicationSummaryVM() {
        return customerApplicationSummaryVM;
    }

    public void setCustomerApplicationSummaryVM(CustomerApplicationSummaryVM customerApplicationSummaryVM) {
        this.customerApplicationSummaryVM = customerApplicationSummaryVM;
    }

    public CustomerLearningFileSummaryVM getCustomerLearningFileSummaryVM() {
        return customerLearningFileSummaryVM;
    }

    public void setCustomerLearningFileSummaryVM(CustomerLearningFileSummaryVM customerLearningFileSummaryVM) {
        this.customerLearningFileSummaryVM = customerLearningFileSummaryVM;
    }

    @Override
    public String toString() {
        return "CustomerLearningFileSummaryVM{" +
            "customerApplicationSummaryVM=" + getCustomerApplicationSummaryVM() +
            ", customerLearningFileSummaryVM=" + getCustomerLearningFileSummaryVM() +
            '}';
    }
}
