package com.isoft.dls.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.isoft.dls.domain.enumeration.WebServiceName;

/**
 * WebService (ws_web_service) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "ws_web_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WebService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "end_point", unique = true)
    private String endPoint;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private WebServiceName name;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "technical_remarks")
    private String technicalRemarks;

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

    @OneToMany(mappedBy = "webService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<WebServiceProperty> webServiceProperties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public WebService endPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public WebServiceName getName() {
        return name;
    }

    public WebService name(WebServiceName name) {
        this.name = name;
        return this;
    }

    public void setName(WebServiceName name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public WebService enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public WebService technicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
        return this;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public WebService createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public WebService createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public WebService lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public WebService lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<WebServiceProperty> getWebServiceProperties() {
        return webServiceProperties;
    }

    public WebService webServiceProperties(Set<WebServiceProperty> webServiceProperties) {
        this.webServiceProperties = webServiceProperties;
        return this;
    }

    public WebService addWebServiceProperty(WebServiceProperty webServiceProperty) {
        this.webServiceProperties.add(webServiceProperty);
        webServiceProperty.setWebService(this);
        return this;
    }

    public WebService removeWebServiceProperty(WebServiceProperty webServiceProperty) {
        this.webServiceProperties.remove(webServiceProperty);
        webServiceProperty.setWebService(null);
        return this;
    }

    public void setWebServiceProperties(Set<WebServiceProperty> webServiceProperties) {
        this.webServiceProperties = webServiceProperties;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WebService)) {
            return false;
        }
        return id != null && id.equals(((WebService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WebService{" +
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
