package com.isoft.dls.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A ApplicationViolation.
 */
@Entity
@Table(name = "application_violation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationViolation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "is_eligible_for_exemption", nullable = false)
    private Long isEligibleForExemption;

    @NotNull
    @Column(name = "is_exempted", nullable = false)
    private Long isExempted;

    @NotNull
    @Column(name = "violation_level", nullable = false)
    private String violationLevel;

    @Column(name = "exemption_process_id")
    private Long exemptionProcessId;

    @Column(name = "exempted_by")
    private String exemptedBy;

    @Column(name = "exemption_date")
    private Instant exemptionDate;

    @NotNull
    @Column(name = "application_id", nullable = false)
    private Long applicationId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ApplicationViolation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public ApplicationViolation isEligibleForExemption(Long isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
        return this;
    }

    public void setIsEligibleForExemption(Long isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public Long getIsExempted() {
        return isExempted;
    }

    public ApplicationViolation isExempted(Long isExempted) {
        this.isExempted = isExempted;
        return this;
    }

    public void setIsExempted(Long isExempted) {
        this.isExempted = isExempted;
    }

    public String getViolationLevel() {
        return violationLevel;
    }

    public ApplicationViolation violationLevel(String violationLevel) {
        this.violationLevel = violationLevel;
        return this;
    }

    public void setViolationLevel(String violationLevel) {
        this.violationLevel = violationLevel;
    }

    public Long getExemptionProcessId() {
        return exemptionProcessId;
    }

    public ApplicationViolation exemptionProcessId(Long exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
        return this;
    }

    public void setExemptionProcessId(Long exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
    }

    public String getExemptedBy() {
        return exemptedBy;
    }

    public ApplicationViolation exemptedBy(String exemptedBy) {
        this.exemptedBy = exemptedBy;
        return this;
    }

    public void setExemptedBy(String exemptedBy) {
        this.exemptedBy = exemptedBy;
    }

    public Instant getExemptionDate() {
        return exemptionDate;
    }

    public ApplicationViolation exemptionDate(Instant exemptionDate) {
        this.exemptionDate = exemptionDate;
        return this;
    }

    public void setExemptionDate(Instant exemptionDate) {
        this.exemptionDate = exemptionDate;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public ApplicationViolation applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ApplicationViolation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ApplicationViolation createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ApplicationViolation lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ApplicationViolation lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationViolation)) {
            return false;
        }
        return id != null && id.equals(((ApplicationViolation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationViolation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", isEligibleForExemption=" + getIsEligibleForExemption() +
            ", isExempted=" + getIsExempted() +
            ", violationLevel='" + getViolationLevel() + "'" +
            ", exemptionProcessId=" + getExemptionProcessId() +
            ", exemptedBy='" + getExemptedBy() + "'" +
            ", exemptionDate='" + getExemptionDate() + "'" +
            ", applicationId=" + getApplicationId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
