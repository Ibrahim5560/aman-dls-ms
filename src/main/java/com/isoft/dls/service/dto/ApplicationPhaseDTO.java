package com.isoft.dls.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.isoft.dls.domain.enumeration.PhaseType;

/**
 * A DTO for the {@link com.isoft.dls.domain.ApplicationPhase} entity.
 */
@ApiModel(description = "ApplicationPhase (dls_application_phase) entity.\n@author Ibrahim Hassanin.")
public class ApplicationPhaseDTO implements Serializable {
    
    private Long id;

    @NotNull
    private PhaseType type;

    @NotNull
    private Integer sequence;

    @NotNull
    private Instant startDate;

    private Instant endDate;

    @NotNull
    private String persona;


    private Long applicationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhaseType getType() {
        return type;
    }

    public void setType(PhaseType type) {
        this.type = type;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
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

        ApplicationPhaseDTO applicationPhaseDTO = (ApplicationPhaseDTO) o;
        if (applicationPhaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationPhaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationPhaseDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", sequence=" + getSequence() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", persona='" + getPersona() + "'" +
            ", applicationId=" + getApplicationId() +
            "}";
    }
}
