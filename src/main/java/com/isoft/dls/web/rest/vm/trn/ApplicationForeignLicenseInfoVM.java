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
//import com.isoft.dls.web.rest.vm.trf.ForeignLicenseTemplateViewVM;
//import com.isoft.dls.web.rest.vm.util.ViewModel;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModel;
//
//@ApiModel(description = "Application Foreign License Info VM. @author Mohammad Qasim.")
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class ApplicationForeignLicenseInfoVM implements ViewModel {
//
//    @JsonProperty("serviceRequestDetails")
//    private ForeignLicenseServiceRequestVM foreignLicenseServiceRequestVM;
//
//    @JsonProperty("applicationDetails")
//    private CustomerIdentityVM customerIdentityVM;
//
//    @JsonProperty("foreignLicenseTemplateDetails")
//    private ForeignLicenseTemplateViewVM foreignLicenseTemplateViewVM;
//
//    public CustomerIdentityVM getCustomerIdentityVM() {
//        return customerIdentityVM;
//    }
//
//    public void setCustomerIdentityVM(CustomerIdentityVM customerIdentityVM) {
//        this.customerIdentityVM = customerIdentityVM;
//    }
//
//    public ForeignLicenseServiceRequestVM getForeignLicenseServiceRequestVM() {
//        return foreignLicenseServiceRequestVM;
//    }
//
//    public void setForeignLicenseTemplateViewVM(ForeignLicenseTemplateViewVM foreignLicenseTemplateViewVM) {
//        this.foreignLicenseTemplateViewVM = foreignLicenseTemplateViewVM;
//    }
//
//    public ForeignLicenseTemplateViewVM getForeignLicenseTemplateViewVM() {
//        return foreignLicenseTemplateViewVM;
//    }
//
//    public void setForeignLicenseServiceRequestVM(ForeignLicenseServiceRequestVM foreignLicenseServiceRequestVM) {
//        this.foreignLicenseServiceRequestVM = foreignLicenseServiceRequestVM;
//    }
//
//    @Override
//    public String toString() {
//        return "CustomerForeignLicenseVM{" +
//            "customerIdentityVM=" + getCustomerIdentityVM() +
//            ", foreignLicenseServiceRequestVM=" + getForeignLicenseServiceRequestVM() +
//            ", foreignLicenseTemplateViewVM=" + getForeignLicenseTemplateViewVM() +
//            '}';
//    }
//}
