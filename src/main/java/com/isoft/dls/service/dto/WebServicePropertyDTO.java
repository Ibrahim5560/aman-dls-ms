package com.isoft.dls.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.isoft.dls.domain.enumeration.WebServicePropertyName;

/**
 * A DTO for the {@link com.isoft.dls.domain.WebServiceProperty} entity.
 */
@ApiModel(description = "WebServiceProperty (ws_web_service_property) entity.\n@author Ibrahim Hassanin.")
public class WebServicePropertyDTO implements Serializable {

    private Long id;

    @NotNull
    private WebServicePropertyName name;

    private String value;

    private String technicalRemarks;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;


    private Long webServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WebServicePropertyName getName() {
        return name;
    }

    public void setName(WebServicePropertyName name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
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

    public Long getWebServiceId() {
        return webServiceId;
    }

    public void setWebServiceId(Long webServiceId) {
        this.webServiceId = webServiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebServicePropertyDTO webServicePropertyDTO = (WebServicePropertyDTO) o;
        if (webServicePropertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), webServicePropertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WebServicePropertyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", webServiceId=" + getWebServiceId() +
            "}";
    }
}
