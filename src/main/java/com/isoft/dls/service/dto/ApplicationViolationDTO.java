package com.isoft.dls.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.dls.domain.ApplicationViolation} entity.
 */
public class ApplicationViolationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Long isEligibleForExemption;

    @NotNull
    private Long isExempted;

    @NotNull
    private String violationLevel;

    private Long exemptionProcessId;

    private String exemptedBy;

    private Instant exemptionDate;

    @NotNull
    private Long applicationId;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;


    private Long applicationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public void setIsEligibleForExemption(Long isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public Long getIsExempted() {
        return isExempted;
    }

    public void setIsExempted(Long isExempted) {
        this.isExempted = isExempted;
    }

    public String getViolationLevel() {
        return violationLevel;
    }

    public void setViolationLevel(String violationLevel) {
        this.violationLevel = violationLevel;
    }

    public Long getExemptionProcessId() {
        return exemptionProcessId;
    }

    public void setExemptionProcessId(Long exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
    }

    public String getExemptedBy() {
        return exemptedBy;
    }

    public void setExemptedBy(String exemptedBy) {
        this.exemptedBy = exemptedBy;
    }

    public Instant getExemptionDate() {
        return exemptionDate;
    }

    public void setExemptionDate(Instant exemptionDate) {
        this.exemptionDate = exemptionDate;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

        ApplicationViolationDTO applicationViolationDTO = (ApplicationViolationDTO) o;
        if (applicationViolationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationViolationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationViolationDTO{" +
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
            ", applicationId=" + getApplicationId() +
            "}";
    }
}
