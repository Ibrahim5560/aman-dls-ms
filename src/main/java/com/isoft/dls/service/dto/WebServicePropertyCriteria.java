package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.isoft.dls.domain.enumeration.WebServicePropertyName;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.dls.domain.WebServiceProperty} entity. This class is used
 * in {@link com.isoft.dls.web.rest.WebServicePropertyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /web-service-properties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WebServicePropertyCriteria implements Serializable, Criteria {
    /**
     * Class for filtering WebServicePropertyName
     */
    public static class WebServicePropertyNameFilter extends Filter<WebServicePropertyName> {

        public WebServicePropertyNameFilter() {
        }

        public WebServicePropertyNameFilter(WebServicePropertyNameFilter filter) {
            super(filter);
        }

        @Override
        public WebServicePropertyNameFilter copy() {
            return new WebServicePropertyNameFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private WebServicePropertyNameFilter name;

    private StringFilter value;

    private StringFilter technicalRemarks;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter webServiceId;

    public WebServicePropertyCriteria(){
    }

    public WebServicePropertyCriteria(WebServicePropertyCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.technicalRemarks = other.technicalRemarks == null ? null : other.technicalRemarks.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.webServiceId = other.webServiceId == null ? null : other.webServiceId.copy();
    }

    @Override
    public WebServicePropertyCriteria copy() {
        return new WebServicePropertyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public WebServicePropertyNameFilter getName() {
        return name;
    }

    public void setName(WebServicePropertyNameFilter name) {
        this.name = name;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
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

    public LongFilter getWebServiceId() {
        return webServiceId;
    }

    public void setWebServiceId(LongFilter webServiceId) {
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
        final WebServicePropertyCriteria that = (WebServicePropertyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(value, that.value) &&
            Objects.equals(technicalRemarks, that.technicalRemarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(webServiceId, that.webServiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        value,
        technicalRemarks,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        webServiceId
        );
    }

    @Override
    public String toString() {
        return "WebServicePropertyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (technicalRemarks != null ? "technicalRemarks=" + technicalRemarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (webServiceId != null ? "webServiceId=" + webServiceId + ", " : "") +
            "}";
    }

}
