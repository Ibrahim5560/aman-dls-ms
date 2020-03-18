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
//import com.isoft.dls.web.rest.vm.util.ViewModel;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModel;
//
//import java.time.LocalDateTime;
//
//@ApiModel(description = "Foreign License Service Request VM. @author Mohammad Qasim.")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class ForeignLicenseServiceRequestVM implements ViewModel {
//
//    @JsonProperty("serviceRequestReferenceNo")
//    private Long serviceReferenceNo;
//
//    @JsonProperty("requestDate")
//    private LocalDateTime statusDate;
//
//    @JsonProperty("foreignLicenseDetails")
//    private ForeignLicenseDetailVM serviceDocument;
//
//    public Long getServiceReferenceNo() {
//        return serviceReferenceNo;
//    }
//
//    public void setServiceReferenceNo(Long serviceReferenceNo) {
//        this.serviceReferenceNo = serviceReferenceNo;
//    }
//
//    public LocalDateTime getStatusDate() {
//        return statusDate;
//    }
//
//    public void setStatusDate(LocalDateTime statusDate) {
//        this.statusDate = statusDate;
//    }
//
//    public ForeignLicenseDetailVM getServiceDocument() {
//        return serviceDocument;
//    }
//
//    public void setServiceDocument(ForeignLicenseDetailVM serviceDocument) {
//        this.serviceDocument = serviceDocument;
//    }
//
//    @Override
//    public String toString() {
//        return "ForeignLicenseDetailVM{" +
//            ", serviceReferenceNo=" + getServiceReferenceNo() +
//            ", statusDate=" + getStatusDate() +
//            ", serviceDocument=" + getServiceDocument() +
//            '}';
//    }
//}
