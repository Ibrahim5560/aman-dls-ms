package com.isoft.dls.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.dls.domain.SysDomainValue} entity.
 */
@ApiModel(description = "SysDomainValue (sys_domain_value) entity.\n@author Ibrahim Hassanin.")
public class SysDomainValueDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;

    @NotNull
    private String description;

    @NotNull
    private Integer sortOrder;

    @NotNull
    private Integer domainId;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;


    private Long sysDomainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
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

    public Long getSysDomainId() {
        return sysDomainId;
    }

    public void setSysDomainId(Long sysDomainId) {
        this.sysDomainId = sysDomainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysDomainValueDTO sysDomainValueDTO = (SysDomainValueDTO) o;
        if (sysDomainValueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysDomainValueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysDomainValueDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", description='" + getDescription() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", domainId=" + getDomainId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", sysDomainId=" + getSysDomainId() +
            "}";
    }
}
