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

//import com.isoft.dls.domain.type.FeeDocumentJsonType;
//import com.isoft.dls.domain.type.ServiceDocumentJsonType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.web.rest.vm.trn.ServiceRequestStatus;
import com.isoft.dls.web.rest.vm.trn.ServiceVM;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;

/**
 * A VM of Payment Service Request API.
 */
@ApiModel(description = "Payment Service Response VM. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentServiceRequestVM {

    @JsonProperty("serviceReferenceNo")
    private Long id;

    @JsonProperty("serviceCode")
    private String serviceCode;

    @JsonProperty("status")
    private ServiceRequestStatus status;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("paymentReference")
    private Long paymentReference;

    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

    @JsonProperty("serviceDetails")
    private ServiceVM serviceDetails;
//
//    @JsonProperty("feeDocument")
//    private FeeDocumentJsonType feeDocument;
//
//    @JsonProperty("serviceDocument")
//    private ServiceDocumentJsonType serviceDocument;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    public ServiceVM getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(ServiceVM serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

//    public FeeDocumentJsonType getFeeDocument() {
//        return feeDocument;
//    }
//
//    public void setFeeDocument(FeeDocumentJsonType feeDocument) {
//        this.feeDocument = feeDocument;
//    }
//
//    public ServiceDocumentJsonType getServiceDocument() {
//        return serviceDocument;
//    }
//
//    public void setServiceDocument(ServiceDocumentJsonType serviceDocument) {
//        this.serviceDocument = serviceDocument;
//    }


    @Override
    public String toString() {
        return "PaymentServiceRequestVM{" +
            "id=" + id +
            ", serviceCode='" + serviceCode + '\'' +
            ", status=" + status +
            ", paymentMethod='" + paymentMethod + '\'' +
            ", paymentReference=" + paymentReference +
            ", paymentDate=" + paymentDate +
            ", serviceDetails=" + serviceDetails +
//            ", feeDocument=" + feeDocument +
//            ", serviceDocument=" + serviceDocument +
            '}';
    }
}
