///*
// * Copyright (c) ISOFT 2020.
// * Ferdous Tower (Takreer Building) , Salam Street
// * Abu Dhabi, United Arab Emirates
// * P.O. Box: 32326
// * All Rights Reserved.
// *
// * ver    Developer          	Date              Comments
// * ----- -----------------  	----------       -----------------
// * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
// */
//
//package com.isoft.dls.web.rest.vm.trn;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.isoft.dls.web.rest.vm.ViewModel;
//import io.swagger.annotations.ApiModel;
//
//import javax.validation.constraints.NotNull;
//
///**
// * A VM of Customer Profile Summary API.
// */
//@ApiModel(description = "customer profile (wrapper of service_request) entity. @author Mohammad Abulawi.")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class CustomerProfileSummaryVM implements ViewModel {
//
//    @NotNull
//    @JsonProperty("applicationReferenceNo")
//    private Long applicationId;
//
//    @NotNull
//    @JsonProperty("customerNo")
//    private Long customerNo;
//
//    @JsonProperty("serviceDocumentInfo")
//    private CustomerProfileSummaryServiceRequestVM customerProfileSummaryServiceRequestVM;
//
////    @JsonProperty("serviceDocument")
////    private ServiceDocumentJsonType serviceDocument;
////
////    public ServiceDocumentJsonType getServiceDocument() {
////        return serviceDocument;
////    }
////
////    public void setServiceDocument(ServiceDocumentJsonType serviceDocument) {
////        this.serviceDocument = serviceDocument;
////    }
//
//    public Long getApplicationId() {
//        return applicationId;
//    }
//
//    public void setApplicationId(Long applicationId) {
//        this.applicationId = applicationId;
//    }
//
//    public CustomerProfileSummaryServiceRequestVM getCustomerProfileSummaryServiceRequestVM() {
//        return customerProfileSummaryServiceRequestVM;
//    }
//
//    public void setCustomerProfileSummaryServiceRequestVM(CustomerProfileSummaryServiceRequestVM customerProfileSummaryServiceRequestVM) {
////        this.setServiceDocument(null);
//        this.customerProfileSummaryServiceRequestVM = customerProfileSummaryServiceRequestVM;
//    }
//
//    public Long getCustomerNo() {
//        return customerNo;
//    }
//
//    public void setCustomerNo(Long customerNo) {
//        this.customerNo = customerNo;
//    }
//
//
//    @Override
//    public String toString() {
//        return "CustomerProfileSummaryVM{" +
//            "applicationId=" + applicationId +
//            ", customerNo=" + customerNo +
//            ", customerProfileSummaryServiceRequestVM=" + customerProfileSummaryServiceRequestVM +
////            ", serviceDocument=" + serviceDocument +
//            '}';
//    }
//}
