package com.isoft.dls.service.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.isoft.dls.domain.SysDomain} entity.
 */
@ApiModel(description = "SysDomain (sys_domain) entity.\n@author Ibrahim Hassanin.")
public class SysDomainDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String description;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;



    @JsonProperty("domainValues")
    private Set<SysDomainValueDTO> domainValues = new HashSet<>();

    public Set<SysDomainValueDTO> getDomainValues() {
        return domainValues;
    }

    public void setDomainValues(Set<SysDomainValueDTO> domainValues) {
        this.domainValues = domainValues;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysDomainDTO sysDomainDTO = (SysDomainDTO) o;
        if (sysDomainDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysDomainDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysDomainDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
