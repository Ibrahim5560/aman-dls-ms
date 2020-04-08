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

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A VM of Initial Product Offer API.
 */
@ApiModel(description = "productOffer (wrapper of trn_application) entity. @author Yousef Abu Amireh.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LearningFileVM implements ViewModel {

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    private String serviceCode;

    @JsonProperty("nocAttachment")
    private String nocAttachment;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getNocAttachment() {
        return nocAttachment;
    }

    public void setNocAttachment(String nocAttachment) {
        this.nocAttachment = nocAttachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LearningFileVM initialProductOfferVM = (LearningFileVM) o;
        if (initialProductOfferVM.getApplicationId() == null || getApplicationId() == null) {
            return false;
        }
        return Objects.equals(getApplicationId(), initialProductOfferVM.getApplicationId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getApplicationId());
    }


    @Override
    public String toString() {
        return "LearningFileVM{" +
            "applicationId=" + applicationId +
            ", serviceCode='" + serviceCode + '\'' +
            ", nocAttachment='" + nocAttachment + '\'' +
            '}';
    }
}
