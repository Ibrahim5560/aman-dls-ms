/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 3:04 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.ntf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.domain.enumeration.ApplicationStatus;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.domain.type.MultilingualJsonType;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A VM of Application Notification Info API.
 */
@ApiModel(description = "Application Notification Info VM (wrapper of trn_application) entity. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerApplicationInfoVM implements ViewModel {

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @NotNull
    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("arabicCustomerName")
    private String arabicCustomerName;

    @JsonProperty("applicationTypeId")
    private Long applicationTypeId;

    @JsonProperty("status")
    private ApplicationStatus status;

    @JsonProperty("statusDescription")
    private MultilingualJsonType statusDescription;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public MultilingualJsonType getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(MultilingualJsonType statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerApplicationInfoVM that = (CustomerApplicationInfoVM) o;
        return Objects.equals(applicationId, that.applicationId) &&
                activePhase == that.activePhase;
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId, activePhase);
    }


    @Override
    public String toString() {
        return "CustomerApplicationInfoVM{" +
            "applicationId=" + applicationId +
            ", activePhase=" + activePhase +
            ", applicationCriteria=" + applicationCriteria +
            ", userId=" + userId +
            ", englishCustomerName='" + englishCustomerName + '\'' +
            ", arabicCustomerName='" + arabicCustomerName + '\'' +
            ", applicationTypeId=" + applicationTypeId +
            ", status=" + status +
            ", statusDescription=" + statusDescription +
            '}';
    }
}
