package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.isoft.dls.domain.enumeration.ViolationLevel;
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
    /**
     * Class for filtering ViolationLevel
     */
    public static class ViolationLevelFilter extends Filter<ViolationLevel> {

        public ViolationLevelFilter() {
        }

        public ViolationLevelFilter(ViolationLevelFilter filter) {
            super(filter);
        }

        @Override
        public ViolationLevelFilter copy() {
            return new ViolationLevelFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private BooleanFilter isEligibleForExemption;

    private BooleanFilter isExempted;

    private ViolationLevelFilter level;

    private LongFilter exemptionProcessId;

    private StringFilter exemptedBy;

    private InstantFilter exemptionDate;

    private LongFilter serviceRequestId;

    private LongFilter applicationId;

    public ApplicationViolationCriteria() {
    }

    public ApplicationViolationCriteria(ApplicationViolationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.isEligibleForExemption = other.isEligibleForExemption == null ? null : other.isEligibleForExemption.copy();
        this.isExempted = other.isExempted == null ? null : other.isExempted.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.exemptionProcessId = other.exemptionProcessId == null ? null : other.exemptionProcessId.copy();
        this.exemptedBy = other.exemptedBy == null ? null : other.exemptedBy.copy();
        this.exemptionDate = other.exemptionDate == null ? null : other.exemptionDate.copy();
        this.serviceRequestId = other.serviceRequestId == null ? null : other.serviceRequestId.copy();
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

    public BooleanFilter getIsEligibleForExemption() {
        return isEligibleForExemption;
    }

    public void setIsEligibleForExemption(BooleanFilter isEligibleForExemption) {
        this.isEligibleForExemption = isEligibleForExemption;
    }

    public BooleanFilter getIsExempted() {
        return isExempted;
    }

    public void setIsExempted(BooleanFilter isExempted) {
        this.isExempted = isExempted;
    }

    public ViolationLevelFilter getLevel() {
        return level;
    }

    public void setLevel(ViolationLevelFilter level) {
        this.level = level;
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

    public LongFilter getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(LongFilter serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
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
            Objects.equals(level, that.level) &&
            Objects.equals(exemptionProcessId, that.exemptionProcessId) &&
            Objects.equals(exemptedBy, that.exemptedBy) &&
            Objects.equals(exemptionDate, that.exemptionDate) &&
            Objects.equals(serviceRequestId, that.serviceRequestId) &&
            Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        code,
        isEligibleForExemption,
        isExempted,
        level,
        exemptionProcessId,
        exemptedBy,
        exemptionDate,
        serviceRequestId,
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
                (level != null ? "level=" + level + ", " : "") +
                (exemptionProcessId != null ? "exemptionProcessId=" + exemptionProcessId + ", " : "") +
                (exemptedBy != null ? "exemptedBy=" + exemptedBy + ", " : "") +
                (exemptionDate != null ? "exemptionDate=" + exemptionDate + ", " : "") +
                (serviceRequestId != null ? "serviceRequestId=" + serviceRequestId + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
            "}";
    }

}
