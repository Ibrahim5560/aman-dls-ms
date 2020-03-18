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
import com.isoft.dls.domain.enumeration.ApplicationStatus;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.domain.type.MultilingualJsonType;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

/**
 * A VM for Customer Application Summary.
 */
@ApiModel(description = "Customer Application VM. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerApplicationSummaryVM implements ViewModel {

    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("status")
    private ApplicationStatus status;

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("arabicCustomerName")
    private String arabicCustomerName;

    @JsonProperty("trafficCodeNo")
    private Long trafficCodeNo;

    @JsonProperty("professionName")
    private MultilingualJsonType professionName;

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

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
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

    public Long getTrafficCodeNo() {
        return trafficCodeNo;
    }

    public void setTrafficCodeNo(Long trafficCodeNo) {
        this.trafficCodeNo = trafficCodeNo;
    }

    public MultilingualJsonType getProfessionName() {
        return professionName;
    }

    public void setProfessionName(MultilingualJsonType professionName) {
        this.professionName = professionName;
    }

    @Override
    public String toString() {
        return "CustomerApplicationSummaryVM {" +
            "applicationId=" + getApplicationId() +
            ", applicationCriteria=" + getApplicationCriteria() +
            ", status=" + getStatus() +
            ", activePhase=" + getActivePhase() +
            ", englishCustomerName='" + getEnglishCustomerName() + '\'' +
            ", arabicCustomerName='" + getArabicCustomerName() + '\'' +
            ", trafficCodeNo='" + getTrafficCodeNo() + '\'' +
            ", professionName='" + getProfessionName() + '\'' +
            '}';
    }
}
