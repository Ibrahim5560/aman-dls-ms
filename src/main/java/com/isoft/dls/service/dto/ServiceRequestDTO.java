package com.isoft.dls.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.enumeration.ServiceRequestStatus;

/**
 * A DTO for the {@link com.isoft.dls.domain.ServiceRequest} entity.
 */
@ApiModel(description = "ServiceRequest (service_request) entity.\n@author Ibrahim Hassanin.")
public class ServiceRequestDTO implements Serializable {
    
    private Long id;

    @Lob
    private String serviceDocument;

    @Lob
    private String feeDocument;

    @NotNull
    private String serviceCode;

    @NotNull
    private PhaseType phaseType;

    @NotNull
    private ServiceRequestStatus status;

    @NotNull
    private String statusDescription;

    @NotNull
    private Instant statusDate;

    private Double totalFeeAmount;

    private String paidBy;

    private Integer paymentMethod;

    private Long paymentReference;

    private Instant paymentDate;

    private String rejectedBy;

    private String rejectionReason;

    private Instant rejectionDate;

    private Long processInstanceId;


    private Long reversedById;

    private Long applicationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceDocument() {
        return serviceDocument;
    }

    public void setServiceDocument(String serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    public String getFeeDocument() {
        return feeDocument;
    }

    public void setFeeDocument(String feeDocument) {
        this.feeDocument = feeDocument;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
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

    public Double getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(Double totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
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

    public Long getReversedById() {
        return reversedById;
    }

    public void setReversedById(Long serviceRequestId) {
        this.reversedById = serviceRequestId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
        if (serviceRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceRequestDTO{" +
            "id=" + getId() +
            ", serviceDocument='" + getServiceDocument() + "'" +
            ", feeDocument='" + getFeeDocument() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", phaseType='" + getPhaseType() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", totalFeeAmount=" + getTotalFeeAmount() +
            ", paidBy='" + getPaidBy() + "'" +
            ", paymentMethod=" + getPaymentMethod() +
            ", paymentReference=" + getPaymentReference() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", rejectedBy='" + getRejectedBy() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            ", rejectionDate='" + getRejectionDate() + "'" +
            ", processInstanceId=" + getProcessInstanceId() +
            ", reversedById=" + getReversedById() +
            ", applicationId=" + getApplicationId() +
            "}";
    }
}
