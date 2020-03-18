package com.isoft.dls.service.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import com.isoft.dls.domain.enumeration.WebServiceName;

/**
 * A DTO for the {@link com.isoft.dls.domain.WebService} entity.
 */
@ApiModel(description = "WebService (ws_web_service) entity.\n@author Ibrahim Hassanin.")
public class WebServiceDTO implements Serializable {

    private Long id;

    private String endPoint;

    @NotNull
    private WebServiceName name;

    private Boolean enabled;

    private String technicalRemarks;

    @NotNull
    private String createdBy;

    @NotNull
    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @JsonProperty("webServiceProperties")
    private List<WebServicePropertyDTO> webServiceProperties;

    public List<WebServicePropertyDTO> getWebServiceProperties() {
        return webServiceProperties;
    }

    public void setWebServiceProperties(List<WebServicePropertyDTO> webServiceProperties) {
        this.webServiceProperties = webServiceProperties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public WebServiceName getName() {
        return name;
    }

    public void setName(WebServiceName name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebServiceDTO webServiceDTO = (WebServiceDTO) o;
        if (webServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), webServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WebServiceDTO{" +
            "id=" + getId() +
            ", endPoint='" + getEndPoint() + "'" +
            ", name='" + getName() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", technicalRemarks='" + getTechnicalRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
