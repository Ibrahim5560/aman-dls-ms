package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.dls.domain.ApplicationViolation} entity. This class is used
 * in {@link com.isoft.dls.web.rest.ApplicationViolationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /application-violations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationViolationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private LongFilter isEligibleForExemption;

    private LongFilter isExempted;

    private StringFilter violationLevel;

    private LongFilter exemptionProcessId;

    private StringFilter exemptedBy;

    private InstantFilter exemptionDate;

    private LongFilter applicationId;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter applicationId;

    public ApplicationViolationCriteria() {
    }

    public ApplicationViolationCriteria(ApplicationViolationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.isEligibleForExemption = other.isEligibleForExemption == null ? null : other.isEligibleForExemption.copy();
        this.isExempted = other.isExempted == null ? null : other.isExempted.copy();
        this.violationLevel = other.violationLevel == null ? null : other.violationLevel.copy();
        this.exemptionProcessId = other.exemptionProcessId == null ? null : other.exemptionProcessId.copy();
        this.exemptedBy = other.exemptedBy == null ? null : other.exemptedBy.copy();
        this.exemptionDate = other.exemptionDate == null ? null : other.exemptionDate.copy();
        this.applicationId = other.applicationId == null ? null : other.applicationId.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.applicationId = other.applicationId == null ? null : other.applicationId.copy();
    }

    @Override
    public ApplicationViolationCriteria copy() {
        return new ApplicationViolationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public LongFilter getIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public void setIsEligibleForExemption(LongFilter isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public LongFilter getIsExempted() {
        return isExempted;
    }

    public void setIsExempted(LongFilter isExempted) {
        this.isExempted = isExempted;
    }

    public StringFilter getViolationLevel() {
        return violationLevel;
    }

    public void setViolationLevel(StringFilter violationLevel) {
        this.violationLevel = violationLevel;
    }

    public LongFilter getExemptionProcessId() {
        return exemptionProcessId;
    }

    public void setExemptionProcessId(LongFilter exemptionProcessId) {
        this.exemptionProcessId = exemptionProcessId;
    }

    public StringFilter getExemptedBy() {
        return exemptedBy;
    }

    public void setExemptedBy(StringFilter exemptedBy) {
        this.exemptedBy = exemptedBy;
    }

    public InstantFilter getExemptionDate() {
        return exemptionDate;
    }

    public void setExemptionDate(InstantFilter exemptionDate) {
        this.exemptionDate = exemptionDate;
    }

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
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

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
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
        final ApplicationViolationCriteria that = (ApplicationViolationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(isEligibleForExemption, that.isEligibleForExemption) &&
            Objects.equals(isExempted, that.isExempted) &&
            Objects.equals(violationLevel, that.violationLevel) &&
            Objects.equals(exemptionProcessId, that.exemptionProcessId) &&
            Objects.equals(exemptedBy, that.exemptedBy) &&
            Objects.equals(exemptionDate, that.exemptionDate) &&
            Objects.equals(applicationId, that.applicationId) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        isEligibleForExemption,
        isExempted,
        violationLevel,
        exemptionProcessId,
        exemptedBy,
        exemptionDate,
        applicationId,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        applicationId
        );
    }

    @Override
    public String toString() {
        return "ApplicationViolationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (isEligibleForExemption != null ? "isEligibleForExemption=" + isEligibleForExemption + ", " : "") +
                (isExempted != null ? "isExempted=" + isExempted + ", " : "") +
                (violationLevel != null ? "violationLevel=" + violationLevel + ", " : "") +
                (exemptionProcessId != null ? "exemptionProcessId=" + exemptionProcessId + ", " : "") +
                (exemptedBy != null ? "exemptedBy=" + exemptedBy + ", " : "") +
                (exemptionDate != null ? "exemptionDate=" + exemptionDate + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
            "}";
    }

}
