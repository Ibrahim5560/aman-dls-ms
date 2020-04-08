package com.isoft.dls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.isoft.dls.domain.enumeration.PhaseType;

import com.isoft.dls.domain.enumeration.ServiceRequestStatus;

/**
 * ServiceRequest (service_request) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "sere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "service_document")
    private String serviceDocument;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fee_document")
    private String feeDocument;

    @NotNull
    @Column(name = "service_code", nullable = false)
    private String serviceCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "phase_type", nullable = false)
    private PhaseType phaseType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServiceRequestStatus status;

    @NotNull
    @Column(name = "status_description", nullable = false)
    private String statusDescription;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private Instant statusDate;

    @Column(name = "total_fee_amount")
    private Double totalFeeAmount;

    @Column(name = "paid_by")
    private String paidBy;

    @Column(name = "payment_method")
    private Integer paymentMethod;

    @Column(name = "payment_reference")
    private Long paymentReference;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Column(name = "rejected_by")
    private String rejectedBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "rejection_date")
    private Instant rejectionDate;

    @Column(name = "process_instance_id")
    private Long processInstanceId;

    @OneToMany(mappedBy = "serviceRequest")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationViolation> applicationViolations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("serviceRequests")
    private ServiceRequest reversedBy;

    @ManyToOne
    @JsonIgnoreProperties("serviceRequests")
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceDocument() {
        return serviceDocument;
    }

    public ServiceRequest serviceDocument(String serviceDocument) {
        this.serviceDocument = serviceDocument;
        return this;
    }

    public void setServiceDocument(String serviceDocument) {
        this.serviceDocument = serviceDocument;
    }

    public String getFeeDocument() {
        return feeDocument;
    }

    public ServiceRequest feeDocument(String feeDocument) {
        this.feeDocument = feeDocument;
        return this;
    }

    public void setFeeDocument(String feeDocument) {
        this.feeDocument = feeDocument;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public ServiceRequest serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public ServiceRequest phaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
        return this;
    }

    public void setPhaseType(PhaseType phaseType) {
        this.phaseType = phaseType;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public ServiceRequest status(ServiceRequestStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public ServiceRequest statusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Instant getStatusDate() {
        return statusDate;
    }

    public ServiceRequest statusDate(Instant statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public Double getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public ServiceRequest totalFeeAmount(Double totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
        return this;
    }

    public void setTotalFeeAmount(Double totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public String getPaidBy() {
        return paidBy;
    }

    public ServiceRequest paidBy(String paidBy) {
        this.paidBy = paidBy;
        return this;
    }

    public void setPaidBy(String paidBy) {
        this.paidBy = paidBy;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public ServiceRequest paymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public ServiceRequest paymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
        return this;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public ServiceRequest paymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public ServiceRequest rejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
        return this;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public ServiceRequest rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Instant getRejectionDate() {
        return rejectionDate;
    }

    public ServiceRequest rejectionDate(Instant rejectionDate) {
        this.rejectionDate = rejectionDate;
        return this;
    }

    public void setRejectionDate(Instant rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public ServiceRequest processInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Set<ApplicationViolation> getApplicationViolations() {
        return applicationViolations;
    }

    public ServiceRequest applicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
        return this;
    }

    public ServiceRequest addApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.add(applicationViolation);
        applicationViolation.setServiceRequest(this);
        return this;
    }

    public ServiceRequest removeApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.remove(applicationViolation);
        applicationViolation.setServiceRequest(null);
        return this;
    }

    public void setApplicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
    }

    public ServiceRequest getReversedBy() {
        return reversedBy;
    }

    public ServiceRequest reversedBy(ServiceRequest serviceRequest) {
        this.reversedBy = serviceRequest;
        return this;
    }

    public void setReversedBy(ServiceRequest serviceRequest) {
        this.reversedBy = serviceRequest;
    }

    public Application getApplication() {
        return application;
    }

    public ServiceRequest application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRequest)) {
            return false;
        }
        return id != null && id.equals(((ServiceRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
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
            "}";
    }
}
