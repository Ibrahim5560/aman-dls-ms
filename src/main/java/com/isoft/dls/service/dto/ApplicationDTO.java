package com.isoft.dls.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.isoft.dls.domain.enumeration.ApplicationStatus;
import com.isoft.dls.domain.enumeration.PhaseType;

/**
 * A DTO for the {@link com.isoft.dls.domain.Application} entity.
 */
@ApiModel(description = "Application (dls_application) entity.\n@author Ibrahim Hassanin.")
public class ApplicationDTO implements Serializable {

    private Long id;

    @NotNull
    private ApplicationStatus status;
    //TODO : jsonType
    @NotNull
    private String statusDescription;

    @NotNull
    private Instant statusDate;

    @NotNull
    private PhaseType activePhase;

    private String confirmedBy;

    private Instant confirmationDate;

    private String rejectedBy;

    private String rejectionReason;

    private Instant rejectionDate;


    private Long processInstanceId;

    @NotNull
    private String channelCode;

    @NotNull
    private String channelDescription;

    private String customerId;

    private String englishCustomerName;

    private String arabicCustomerName;

    private String tradeLicenseNo;

    private String englishCorporateName;

    private String arabicCorporateName;

    @NotNull
    private String userType;
    //TODO : jsonType
    @NotNull
    private String userTypeDescription;

    @NotNull
    private String userRole;
    //TODO : jsonType
    private String applicationCriteria;

    private String persona;

    private String personaVersion;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long applicationTypeId;

    @JsonProperty("serviceRequests")
    private Set<ServiceRequestDTO> serviceRequests = new HashSet<>();

    public Set<ServiceRequestDTO> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(Set<ServiceRequestDTO> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Instant getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Instant statusDate) {
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

    public Instant getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Instant confirmationDate) {
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

    public Instant getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Instant rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getEnglishCorporateName() {
        return englishCorporateName;
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

    public String getUserTypeDescription() {
        return userTypeDescription;
    }

    public void setUserTypeDescription(String userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(String applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getPersonaVersion() {
        return personaVersion;
    }

    public void setPersonaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationDTO applicationDTO = (ApplicationDTO) o;
        if (applicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", activePhase='" + getActivePhase() + "'" +
            ", confirmedBy='" + getConfirmedBy() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", rejectedBy='" + getRejectedBy() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            ", rejectionDate='" + getRejectionDate() + "'" +
            ", processInstanceId=" + getProcessInstanceId() +
            ", channelCode='" + getChannelCode() + "'" +
            ", channelDescription='" + getChannelDescription() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", englishCustomerName='" + getEnglishCustomerName() + "'" +
            ", arabicCustomerName='" + getArabicCustomerName() + "'" +
            ", tradeLicenseNo='" + getTradeLicenseNo() + "'" +
            ", englishCorporateName='" + getEnglishCorporateName() + "'" +
            ", arabicCorporateName='" + getArabicCorporateName() + "'" +
            ", userType='" + getUserType() + "'" +
            ", userTypeDescription='" + getUserTypeDescription() + "'" +
            ", userRole='" + getUserRole() + "'" +
            ", applicationCriteria='" + getApplicationCriteria() + "'" +
            ", persona='" + getPersona() + "'" +
            ", personaVersion='" + getPersonaVersion() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", applicationTypeId=" + getApplicationTypeId() +
            "}";
    }
}
