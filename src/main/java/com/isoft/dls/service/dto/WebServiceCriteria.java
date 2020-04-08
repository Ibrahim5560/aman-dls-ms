package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.isoft.dls.domain.enumeration.WebServiceName;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.dls.domain.WebService} entity. This class is used
 * in {@link com.isoft.dls.web.rest.WebServiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /web-services?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WebServiceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering WebServiceName
     */
    public static class WebServiceNameFilter extends Filter<WebServiceName> {

        public WebServiceNameFilter() {
        }

        public WebServiceNameFilter(WebServiceNameFilter filter) {
            super(filter);
        }

        @Override
        public WebServiceNameFilter copy() {
            return new WebServiceNameFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter endPoint;

    private WebServiceNameFilter name;

    private BooleanFilter enabled;

    private StringFilter technicalRemarks;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter webServicePropertyId;

    public WebServiceCriteria(){
    }

    public WebServiceCriteria(WebServiceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.endPoint = other.endPoint == null ? null : other.endPoint.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.enabled = other.enabled == null ? null : other.enabled.copy();
        this.technicalRemarks = other.technicalRemarks == null ? null : other.technicalRemarks.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.webServicePropertyId = other.webServicePropertyId == null ? null : other.webServicePropertyId.copy();
    }

    @Override
    public WebServiceCriteria copy() {
        return new WebServiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(StringFilter endPoint) {
        this.endPoint = endPoint;
    }

    public WebServiceNameFilter getName() {
        return name;
    }

    public void setName(WebServiceNameFilter name) {
        this.name = name;
    }

    public BooleanFilter getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanFilter enabled) {
        this.enabled = enabled;
    }

    public StringFilter getTechnicalRemarks() {
        return technicalRemarks;
    }

    public void setTechnicalRemarks(StringFilter technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getWebServicePropertyId() {
        return webServicePropertyId;
    }

    public void setWebServicePropertyId(LongFilter webServicePropertyId) {
        this.webServicePropertyId = webServicePropertyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WebServiceCriteria that = (WebServiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(endPoint, that.endPoint) &&
            Objects.equals(name, that.name) &&
            Objects.equals(enabled, that.enabled) &&
            Objects.equals(technicalRemarks, that.technicalRemarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(webServicePropertyId, that.webServicePropertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        endPoint,
        name,
        enabled,
        technicalRemarks,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        webServicePropertyId
        );
    }

    @Override
    public String toString() {
        return "WebServiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (endPoint != null ? "endPoint=" + endPoint + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (enabled != null ? "enabled=" + enabled + ", " : "") +
                (technicalRemarks != null ? "technicalRemarks=" + technicalRemarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (webServicePropertyId != null ? "webServicePropertyId=" + webServicePropertyId + ", " : "") +
            "}";
    }

}
