package com.isoft.dls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.isoft.dls.domain.enumeration.ApplicationStatus;

import com.isoft.dls.domain.enumeration.PhaseType;

/**
 * Application (dls_application) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "appl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @NotNull
    @Column(name = "status_description", nullable = false)
    private String statusDescription;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private Instant statusDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "active_phase", nullable = false)
    private PhaseType activePhase;

    @Column(name = "confirmed_by")
    private String confirmedBy;

    @Column(name = "confirmation_date")
    private Instant confirmationDate;

    @Column(name = "rejected_by")
    private String rejectedBy;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "rejection_date")
    private Instant rejectionDate;

    
    @Column(name = "process_instance_id", unique = true)
    private Long processInstanceId;

    @NotNull
    @Column(name = "channel_code", nullable = false)
    private String channelCode;

    @NotNull
    @Column(name = "channel_description", nullable = false)
    private String channelDescription;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "english_customer_name")
    private String englishCustomerName;

    @Column(name = "arabic_customer_name")
    private String arabicCustomerName;

    @Column(name = "trade_license_no")
    private String tradeLicenseNo;

    @Column(name = "english_corporate_name")
    private String englishCorporateName;

    @Column(name = "arabic_corporate_name")
    private String arabicCorporateName;

    @NotNull
    @Column(name = "user_type", nullable = false)
    private String userType;

    @NotNull
    @Column(name = "user_type_description", nullable = false)
    private String userTypeDescription;

    @NotNull
    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Column(name = "application_criteria")
    private String applicationCriteria;

    @Column(name = "persona")
    private String persona;

    @Column(name = "persona_version")
    private String personaVersion;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationPhase> applicationPhases = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceRequest> serviceRequests = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationViolation> applicationViolations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("applications")
    private ApplicationType applicationType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public Application status(ApplicationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public Application statusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Instant getStatusDate() {
        return statusDate;
    }

    public Application statusDate(Instant statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public Application activePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
        return this;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public Application confirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
        return this;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public Instant getConfirmationDate() {
        return confirmationDate;
    }

    public Application confirmationDate(Instant confirmationDate) {
        this.confirmationDate = confirmationDate;
        return this;
    }

    public void setConfirmationDate(Instant confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public Application rejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
        return this;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Application rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Instant getRejectionDate() {
        return rejectionDate;
    }

    public Application rejectionDate(Instant rejectionDate) {
        this.rejectionDate = rejectionDate;
        return this;
    }

    public void setRejectionDate(Instant rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public Application processInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public Application channelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public Application channelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
        return this;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Application customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEnglishCustomerName() {
        return englishCustomerName;
    }

    public Application englishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
        return this;
    }

    public void setEnglishCustomerName(String englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    public String getArabicCustomerName() {
        return arabicCustomerName;
    }

    public Application arabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
        return this;
    }

    public void setArabicCustomerName(String arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    public String getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public Application tradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
        return this;
    }

    public void setTradeLicenseNo(String tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public String getEnglishCorporateName() {
        return englishCorporateName;
    }

    public Application englishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
        return this;
    }

    public void setEnglishCorporateName(String englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
    }

    public String getArabicCorporateName() {
        return arabicCorporateName;
    }

    public Application arabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
        return this;
    }

    public void setArabicCorporateName(String arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
    }

    public String getUserType() {
        return userType;
    }

    public Application userType(String userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeDescription() {
        return userTypeDescription;
    }

    public Application userTypeDescription(String userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
        return this;
    }

    public void setUserTypeDescription(String userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getUserRole() {
        return userRole;
    }

    public Application userRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getApplicationCriteria() {
        return applicationCriteria;
    }

    public Application applicationCriteria(String applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
        return this;
    }

    public void setApplicationCriteria(String applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public String getPersona() {
        return persona;
    }

    public Application persona(String persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getPersonaVersion() {
        return personaVersion;
    }

    public Application personaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
        return this;
    }

    public void setPersonaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Application createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Application createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Application lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Application lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<ApplicationPhase> getApplicationPhases() {
        return applicationPhases;
    }

    public Application applicationPhases(Set<ApplicationPhase> applicationPhases) {
        this.applicationPhases = applicationPhases;
        return this;
    }

    public Application addApplicationPhase(ApplicationPhase applicationPhase) {
        this.applicationPhases.add(applicationPhase);
        applicationPhase.setApplication(this);
        return this;
    }

    public Application removeApplicationPhase(ApplicationPhase applicationPhase) {
        this.applicationPhases.remove(applicationPhase);
        applicationPhase.setApplication(null);
        return this;
    }

    public void setApplicationPhases(Set<ApplicationPhase> applicationPhases) {
        this.applicationPhases = applicationPhases;
    }

    public Set<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public Application serviceRequests(Set<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
        return this;
    }

    public Application addServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.add(serviceRequest);
        serviceRequest.setApplication(this);
        return this;
    }

    public Application removeServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequests.remove(serviceRequest);
        serviceRequest.setApplication(null);
        return this;
    }

    public void setServiceRequests(Set<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public Set<ApplicationViolation> getApplicationViolations() {
        return applicationViolations;
    }

    public Application applicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
        return this;
    }

    public Application addApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.add(applicationViolation);
        applicationViolation.setApplication(this);
        return this;
    }

    public Application removeApplicationViolation(ApplicationViolation applicationViolation) {
        this.applicationViolations.remove(applicationViolation);
        applicationViolation.setApplication(null);
        return this;
    }

    public void setApplicationViolations(Set<ApplicationViolation> applicationViolations) {
        this.applicationViolations = applicationViolations;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public Application applicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
        return this;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Application{" +
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
            "}";
    }
}
