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
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.common.util.brm.calculation.fee.FeeVM;
import com.isoft.dls.domain.enumeration.ApplicationStatus;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.domain.type.MultilingualJsonType;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A VM for the customer application summary application entity.
 */
@ApiModel(description = "service request application (trn_service_request) entity. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessInstanceApplicationVM {

    @JsonProperty("applicationReferenceNo")
    private Long id;

    @JsonProperty("taskId")
    private Long taskId;

    @JsonProperty("assigned")
    private Boolean assigned;

    @JsonProperty("applicationTypeId")
    private Long applicationTypeId;

    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("channelCode")
    private String channelCode;

    @JsonProperty("status")
    private ApplicationStatus status;

    @JsonProperty("statusDescription")
    private MultilingualJsonType statusDescription;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("activePhaseDescription")
    private MultilingualJsonType activePhaseDescription;

    @JsonProperty("confirmedBy")
    private String confirmedBy;

    @JsonProperty("confirmationDate")
    private LocalDateTime confirmationDate;

    @JsonProperty("rejectedBy")
    private String rejectedBy;

    @JsonProperty("rejectionReason")
    private String rejectionReason;

    @JsonProperty("rejectionDate")
    private LocalDateTime rejectionDate;

    @JsonProperty("processInstanceId")
    private Long processInstanceId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("englishCustomerName")
    private String englishCustomerName;

    @JsonProperty("arabicCustomerName")
    private String arabicCustomerName;

    @JsonProperty("tradeLicenseNo")
    private String tradeLicenseNo;

    @JsonProperty("englishCorporateName")
    private String englishCorporateName;

    @JsonProperty("arabicCorporateName")
    private String arabicCorporateName;

    @JsonProperty("userType")
    private String userType;

    @JsonProperty("userTypeDescription")
    private MultilingualJsonType userTypeDescription;

    @JsonProperty("userRole")
    private String userRole;

    @JsonProperty("persona")
    private String persona;

    @JsonProperty("customerInfo")
    private ApplicationCustomerInfoVM customerInfo;

    @JsonProperty("applicationFees")
    private List<FeeVM> applicationFeesList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnglishCustomerName() {
        return StringUtil.formatString(englishCustomerName);
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

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getEnglishCorporateName() {
        return StringUtil.formatString(englishCorporateName);
    }

    public void setEnglishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
    }

    public String getArabicCorporateName() {
        return arabicCorporateName;
    }

    public void setArabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public MultilingualJsonType getUserTypeDescription() {
        return userTypeDescription;
    }

    public void setUserTypeDescription(MultilingualJsonType userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public MultilingualJsonType getActivePhaseDescription() {
        return activePhaseDescription;
    }

    public void setActivePhaseDescription(MultilingualJsonType activePhaseDescription) {
        this.activePhaseDescription = activePhaseDescription;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }


    public ApplicationCustomerInfoVM getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(ApplicationCustomerInfoVM customerInfo) {
        this.customerInfo = customerInfo;
    }

    public List<FeeVM> getApplicationFeesList() {
        return applicationFeesList;
    }

    public void setApplicationFeesList(List<FeeVM> applicationFeesList) {
        this.applicationFeesList = applicationFeesList;
    }

    @Override
    public String toString() {
        return "ProcessInstanceApplicationVM{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", assigned=" + assigned +
                ", applicationTypeId=" + applicationTypeId +
                ", applicationCriteria=" + applicationCriteria +
                ", channelCode='" + channelCode + '\'' +
                ", status=" + status +
                ", statusDescription=" + statusDescription +
                ", statusDate=" + statusDate +
                ", activePhase=" + activePhase +
                ", activePhaseDescription=" + activePhaseDescription +
                ", confirmedBy='" + confirmedBy + '\'' +
                ", confirmationDate=" + confirmationDate +
                ", rejectedBy='" + rejectedBy + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", rejectionDate=" + rejectionDate +
                ", processInstanceId=" + processInstanceId +
                ", userId=" + userId +
                ", englishCustomerName='" + englishCustomerName + '\'' +
                ", arabicCustomerName='" + arabicCustomerName + '\'' +
                ", tradeLicenseNo='" + tradeLicenseNo + '\'' +
                ", englishCorporateName='" + englishCorporateName + '\'' +
                ", arabicCorporateName='" + arabicCorporateName + '\'' +
                ", userType='" + userType + '\'' +
                ", userTypeDescription=" + userTypeDescription +
                ", userRole='" + userRole + '\'' +
                ", persona='" + persona + '\'' +
                ", customerInfo=" + customerInfo +
                ", feesList=" + applicationFeesList +
                '}';
    }
}
