package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.isoft.dls.domain.enumeration.PhaseType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.dls.domain.ApplicationPhase} entity. This class is used
 * in {@link com.isoft.dls.web.rest.ApplicationPhaseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /application-phases?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationPhaseCriteria implements Serializable, Criteria {
    /**
     * Class for filtering PhaseType
     */
    public static class PhaseTypeFilter extends Filter<PhaseType> {

        public PhaseTypeFilter() {
        }

        public PhaseTypeFilter(PhaseTypeFilter filter) {
            super(filter);
        }

        @Override
        public PhaseTypeFilter copy() {
            return new PhaseTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private PhaseTypeFilter type;

    private IntegerFilter sequence;

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter persona;

    private LongFilter applicationId;

    public ApplicationPhaseCriteria() {
    }

    public ApplicationPhaseCriteria(ApplicationPhaseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.sequence = other.sequence == null ? null : other.sequence.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.persona = other.persona == null ? null : other.persona.copy();
        this.applicationId = other.applicationId == null ? null : other.applicationId.copy();
    }

    @Override
    public ApplicationPhaseCriteria copy() {
        return new ApplicationPhaseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public PhaseTypeFilter getType() {
        return type;
    }

    public void setType(PhaseTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getSequence() {
        return sequence;
    }

    public void setSequence(IntegerFilter sequence) {
        this.sequence = sequence;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getPersona() {
        return persona;
    }

    public void setPersona(StringFilter persona) {
        this.persona = persona;
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
        final ApplicationPhaseCriteria that = (ApplicationPhaseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(sequence, that.sequence) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(persona, that.persona) &&
            Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        sequence,
        startDate,
        endDate,
        persona,
        applicationId
        );
    }

    @Override
    public String toString() {
        return "ApplicationPhaseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (sequence != null ? "sequence=" + sequence + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (persona != null ? "persona=" + persona + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
            "}";
    }

}
