package com.isoft.dls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.isoft.dls.domain.enumeration.ViolationLevel;

/**
 * ApplicationViolation (dls_application_violation) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "apvi")
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
    private Boolean isEligibleForExemption;

    @NotNull
    @Column(name = "is_exempted", nullable = false)
    private Boolean isExempted;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private ViolationLevel level;

    @Column(name = "exemption_process_id")
    private Long exemptionProcessId;

    @Column(name = "exempted_by")
    private String exemptedBy;

    @Column(name = "exemption_date")
    private Instant exemptionDate;

    @ManyToOne
    @JsonIgnoreProperties("applicationViolations")
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JsonIgnoreProperties("applicationViolations")
    private Application application;

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

    public Boolean isIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public ApplicationViolation isEligibleForExemption(Boolean isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
        return this;
    }

    public void setIsEligibleForExemption(Boolean isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public Boolean isIsExempted() {
        return isExempted;
    }

    public ApplicationViolation isExempted(Boolean isExempted) {
        this.isExempted = isExempted;
        return this;
    }

    public void setIsExempted(Boolean isExempted) {
        this.isExempted = isExempted;
    }

    public ViolationLevel getLevel() {
        return level;
    }

    public ApplicationViolation level(ViolationLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(ViolationLevel level) {
        this.level = level;
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

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public ApplicationViolation serviceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
        return this;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationViolation application(Application application) {
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
            ", isEligibleForExemption='" + isIsEligibleForExemption() + "'" +
            ", isExempted='" + isIsExempted() + "'" +
            ", level='" + getLevel() + "'" +
            ", exemptionProcessId=" + getExemptionProcessId() +
            ", exemptedBy='" + getExemptedBy() + "'" +
            ", exemptionDate='" + getExemptionDate() + "'" +
            "}";
    }
}
