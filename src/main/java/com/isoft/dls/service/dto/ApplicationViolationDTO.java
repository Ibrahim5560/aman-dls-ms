package com.isoft.dls.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.isoft.dls.domain.enumeration.ViolationLevel;

/**
 * A DTO for the {@link com.isoft.dls.domain.ApplicationViolation} entity.
 */
@ApiModel(description = "ApplicationViolation (dls_application_violation) entity.\n@author Ibrahim Hassanin.")
public class ApplicationViolationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean isEligibleForExemption;

    @NotNull
    private Boolean isExempted;

    @NotNull
    private ViolationLevel level;

    private Long exemptionProcessId;

    private String exemptedBy;

    private Instant exemptionDate;


    private Long serviceRequestId;

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

    public Boolean isIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public void setIsEligibleForExemption(Boolean isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public Boolean isIsExempted() {
        return isExempted;
    }

    public void setIsExempted(Boolean isExempted) {
        this.isExempted = isExempted;
    }

    public ViolationLevel getLevel() {
        return level;
    }

    public void setLevel(ViolationLevel level) {
        this.level = level;
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

    public Long getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(Long serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
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
            ", isEligibleForExemption='" + isIsEligibleForExemption() + "'" +
            ", isExempted='" + isIsExempted() + "'" +
            ", level='" + getLevel() + "'" +
            ", exemptionProcessId=" + getExemptionProcessId() +
            ", exemptedBy='" + getExemptedBy() + "'" +
            ", exemptionDate='" + getExemptionDate() + "'" +
            ", serviceRequestId=" + getServiceRequestId() +
            ", applicationId=" + getApplicationId() +
            "}";
    }
}
