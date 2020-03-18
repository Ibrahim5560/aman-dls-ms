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

import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import java.util.HashSet;
import java.util.Set;

/**
 * A VM of Create Payment Request API.
 */
@ApiModel(description = "Payment Request Response VM. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreatePaymentRequestVM {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("arabicCustomerName")
    private String arabicCustomerName;

    @JsonProperty("serviceRequests")
    private Set<PaymentServiceRequestVM> serviceRequests = new HashSet<>();

    @JsonProperty("paymentReference")
    private Long paymentReference;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public Set<PaymentServiceRequestVM> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(Set<PaymentServiceRequestVM> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public String getEnglishCustomerName() {
        return englishCustomerName;
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    public String getArabicCustomerName() {
        return arabicCustomerName;
    }

    public void setArabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }


    @Override
    public String toString() {
        return "CreatePaymentRequestVM{" +
            "applicationId=" + applicationId +
            ", applicationCriteria=" + applicationCriteria +
            ", englishCustomerName='" + englishCustomerName + '\'' +
            ", arabicCustomerName='" + arabicCustomerName + '\'' +
            ", serviceRequests=" + serviceRequests +
            ", paymentReference=" + paymentReference +
            '}';
    }
}
