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

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "status_description")
    private byte[] statusDescription;

    @Column(name = "status_description_content_type")
    private String statusDescriptionContentType;

    @NotNull
    @Column(name = "status_date", nullable = false)
    private Instant statusDate;

    @Column(name = "active_phase")
    private String activePhase;

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

    @Column(name = "user_id")
    private Long userId;

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

    @Lob
    @Column(name = "user_type_description")
    private byte[] userTypeDescription;

    @Column(name = "user_type_description_content_type")
    private String userTypeDescriptionContentType;

    @NotNull
    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Lob
    @Column(name = "application_criteria")
    private byte[] applicationCriteria;

    @Column(name = "application_criteria_content_type")
    private String applicationCriteriaContentType;

    @NotNull
    @Column(name = "application_type_id", nullable = false)
    private Long applicationTypeId;

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

    @Column(name = "synched_entity_id")
    private Long synchedEntityId;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationPhase> applicationPhases = new HashSet<>();

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

    public String getStatus() {
        return status;
    }

    public Application status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getStatusDescription() {
        return statusDescription;
    }

    public Application statusDescription(byte[] statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    public void setStatusDescription(byte[] statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusDescriptionContentType() {
        return statusDescriptionContentType;
    }

    public Application statusDescriptionContentType(String statusDescriptionContentType) {
        this.statusDescriptionContentType = statusDescriptionContentType;
        return this;
    }

    public void setStatusDescriptionContentType(String statusDescriptionContentType) {
        this.statusDescriptionContentType = statusDescriptionContentType;
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

    public String getActivePhase() {
        return activePhase;
    }

    public Application activePhase(String activePhase) {
        this.activePhase = activePhase;
        return this;
    }

    public void setActivePhase(String activePhase) {
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

    public Long getUserId() {
        return userId;
    }

    public Application userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public byte[] getUserTypeDescription() {
        return userTypeDescription;
    }

    public Application userTypeDescription(byte[] userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
        return this;
    }

    public void setUserTypeDescription(byte[] userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }

    public String getUserTypeDescriptionContentType() {
        return userTypeDescriptionContentType;
    }

    public Application userTypeDescriptionContentType(String userTypeDescriptionContentType) {
        this.userTypeDescriptionContentType = userTypeDescriptionContentType;
        return this;
    }

    public void setUserTypeDescriptionContentType(String userTypeDescriptionContentType) {
        this.userTypeDescriptionContentType = userTypeDescriptionContentType;
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

    public byte[] getApplicationCriteria() {
        return applicationCriteria;
    }

    public Application applicationCriteria(byte[] applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
        return this;
    }

    public void setApplicationCriteria(byte[] applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public String getApplicationCriteriaContentType() {
        return applicationCriteriaContentType;
    }

    public Application applicationCriteriaContentType(String applicationCriteriaContentType) {
        this.applicationCriteriaContentType = applicationCriteriaContentType;
        return this;
    }

    public void setApplicationCriteriaContentType(String applicationCriteriaContentType) {
        this.applicationCriteriaContentType = applicationCriteriaContentType;
    }

    public Long getApplicationTypeId() {
        return applicationTypeId;
    }

    public Application applicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
        return this;
    }

    public void setApplicationTypeId(Long applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
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

    public Long getSynchedEntityId() {
        return synchedEntityId;
    }

    public Application synchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
        return this;
    }

    public void setSynchedEntityId(Long synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
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
            ", statusDescriptionContentType='" + getStatusDescriptionContentType() + "'" +
            ", statusDate='" + getStatusDate() + "'" +
            ", activePhase='" + getActivePhase() + "'" +
            ", confirmedBy='" + getConfirmedBy() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", rejectedBy='" + getRejectedBy() + "'" +
            ", rejectionReason='" + getRejectionReason() + "'" +
            ", rejectionDate='" + getRejectionDate() + "'" +
            ", processInstanceId=" + getProcessInstanceId() +
            ", channelCode='" + getChannelCode() + "'" +
            ", userId=" + getUserId() +
            ", englishCustomerName='" + getEnglishCustomerName() + "'" +
            ", arabicCustomerName='" + getArabicCustomerName() + "'" +
            ", tradeLicenseNo='" + getTradeLicenseNo() + "'" +
            ", englishCorporateName='" + getEnglishCorporateName() + "'" +
            ", arabicCorporateName='" + getArabicCorporateName() + "'" +
            ", userType='" + getUserType() + "'" +
            ", userTypeDescription='" + getUserTypeDescription() + "'" +
            ", userTypeDescriptionContentType='" + getUserTypeDescriptionContentType() + "'" +
            ", userRole='" + getUserRole() + "'" +
            ", applicationCriteria='" + getApplicationCriteria() + "'" +
            ", applicationCriteriaContentType='" + getApplicationCriteriaContentType() + "'" +
            ", applicationTypeId=" + getApplicationTypeId() +
            ", persona='" + getPersona() + "'" +
            ", personaVersion='" + getPersonaVersion() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", synchedEntityId=" + getSynchedEntityId() +
            "}";
    }
}
