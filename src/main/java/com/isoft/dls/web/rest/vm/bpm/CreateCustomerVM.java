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

//import com.isoft.dls.domain.type.ServiceDocumentJsonType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.web.rest.vm.trn.ServiceRequestStatus;
import io.swagger.annotations.ApiModel;

/**
 * A VM of Create Traffic File Service Request API.
 */
@ApiModel(description = "Payment Request Response VM. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateCustomerVM {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("applicationDetails")
    private CustomerApplicationVM customerApplicationVM;

    @JsonProperty("serviceReferenceNo")
    private Long serviceRequestId;
//
//    @JsonProperty("serviceDocument")
//    private ServiceDocumentJsonType serviceDocument;

    @JsonProperty("paymentReference")
    private Long paymentReference;

    @JsonProperty("status")
    private ServiceRequestStatus status;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public CustomerApplicationVM getCustomerApplicationVM() {
        return customerApplicationVM;
    }

    public void setCustomerApplicationVM(CustomerApplicationVM customerApplicationVM) {
        this.customerApplicationVM = customerApplicationVM;
    }

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

//    public ServiceDocumentJsonType getServiceDocument() {
//        return serviceDocument;
//    }
//
//    public void setServiceDocument(ServiceDocumentJsonType serviceDocument) {
//        this.serviceDocument = serviceDocument;
//    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "CreateCustomerVM{" +
            "applicationId=" + applicationId +
            ", customerApplicationVM=" + customerApplicationVM +
            ", serviceRequestId=" + serviceRequestId +
//            ", serviceDocument=" + serviceDocument +
            ", paymentReference=" + paymentReference +
            ", status=" + status +
            '}';
    }
}
