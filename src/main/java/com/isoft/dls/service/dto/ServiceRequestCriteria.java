package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.domain.enumeration.ServiceRequestStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.dls.domain.ServiceRequest} entity. This class is used
 * in {@link com.isoft.dls.web.rest.ServiceRequestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-requests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceRequestCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering ServiceRequestStatus
     */
    public static class ServiceRequestStatusFilter extends Filter<ServiceRequestStatus> {

        public ServiceRequestStatusFilter() {
        }

        public ServiceRequestStatusFilter(ServiceRequestStatusFilter filter) {
            super(filter);
        }

        @Override
        public ServiceRequestStatusFilter copy() {
            return new ServiceRequestStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceCode;

    private PhaseTypeFilter phaseType;

    private ServiceRequestStatusFilter status;

    private StringFilter statusDescription;

    private InstantFilter statusDate;

    private DoubleFilter totalFeeAmount;

    private StringFilter paidBy;

    private IntegerFilter paymentMethod;

    private LongFilter paymentReference;

    private InstantFilter paymentDate;

    private StringFilter rejectedBy;

    private StringFilter rejectionReason;

    private InstantFilter rejectionDate;

    private LongFilter processInstanceId;

    private LongFilter applicationViolationId;

    private LongFilter reversedById;

    private LongFilter applicationId;

    public ServiceRequestCriteria() {
    }

    public ServiceRequestCriteria(ServiceRequestCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serviceCode = other.serviceCode == null ? null : other.serviceCode.copy();
        this.phaseType = other.phaseType == null ? null : other.phaseType.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.statusDescription = other.statusDescription == null ? null : other.statusDescription.copy();
        this.statusDate = other.statusDate == null ? null : other.statusDate.copy();
        this.totalFeeAmount = other.totalFeeAmount == null ? null : other.totalFeeAmount.copy();
        this.paidBy = other.paidBy == null ? null : other.paidBy.copy();
        this.paymentMethod = other.paymentMethod == null ? null : other.paymentMethod.copy();
        this.paymentReference = other.paymentReference == null ? null : other.paymentReference.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.rejectedBy = other.rejectedBy == null ? null : other.rejectedBy.copy();
        this.rejectionReason = other.rejectionReason == null ? null : other.rejectionReason.copy();
        this.rejectionDate = other.rejectionDate == null ? null : other.rejectionDate.copy();
        this.processInstanceId = other.processInstanceId == null ? null : other.processInstanceId.copy();
        this.applicationViolationId = other.applicationViolationId == null ? null : other.applicationViolationId.copy();
        this.reversedById = other.reversedById == null ? null : other.reversedById.copy();
        this.applicationId = other.applicationId == null ? null : other.applicationId.copy();
    }

    @Override
    public ServiceRequestCriteria copy() {
        return new ServiceRequestCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(StringFilter serviceCode) {
        this.serviceCode = serviceCode;
    }

    public PhaseTypeFilter getPhaseType() {
        return phaseType;
    }

    public void setPhaseType(PhaseTypeFilter phaseType) {
        this.phaseType = phaseType;
    }

    public ServiceRequestStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatusFilter status) {
        this.status = status;
    }

    public StringFilter getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(StringFilter statusDescription) {
        this.statusDescription = statusDescription;
    }

    public InstantFilter getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(InstantFilter statusDate) {
        this.statusDate = statusDate;
    }

    public DoubleFilter getTotalFeeAmount() {
        return totalFeeAmount;
    }

    public void setTotalFeeAmount(DoubleFilter totalFeeAmount) {
        this.totalFeeAmount = totalFeeAmount;
    }

    public StringFilter getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(StringFilter paidBy) {
        this.paidBy = paidBy;
    }

    public IntegerFilter getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(IntegerFilter paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LongFilter getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(LongFilter paymentReference) {
        this.paymentReference = paymentReference;
    }

    public InstantFilter getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(InstantFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StringFilter getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(StringFilter rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public StringFilter getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(StringFilter rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public InstantFilter getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(InstantFilter rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public LongFilter getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(LongFilter processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public LongFilter getApplicationViolationId() {
        return applicationViolationId;
    }

    public void setApplicationViolationId(LongFilter applicationViolationId) {
        this.applicationViolationId = applicationViolationId;
    }

    public LongFilter getReversedById() {
        return reversedById;
    }

    public void setReversedById(LongFilter reversedById) {
        this.reversedById = reversedById;
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
        final ServiceRequestCriteria that = (ServiceRequestCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceCode, that.serviceCode) &&
            Objects.equals(phaseType, that.phaseType) &&
            Objects.equals(status, that.status) &&
            Objects.equals(statusDescription, that.statusDescription) &&
            Objects.equals(statusDate, that.statusDate) &&
            Objects.equals(totalFeeAmount, that.totalFeeAmount) &&
            Objects.equals(paidBy, that.paidBy) &&
            Objects.equals(paymentMethod, that.paymentMethod) &&
            Objects.equals(paymentReference, that.paymentReference) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(rejectedBy, that.rejectedBy) &&
            Objects.equals(rejectionReason, that.rejectionReason) &&
            Objects.equals(rejectionDate, that.rejectionDate) &&
            Objects.equals(processInstanceId, that.processInstanceId) &&
            Objects.equals(applicationViolationId, that.applicationViolationId) &&
            Objects.equals(reversedById, that.reversedById) &&
            Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceCode,
        phaseType,
        status,
        statusDescription,
        statusDate,
        totalFeeAmount,
        paidBy,
        paymentMethod,
        paymentReference,
        paymentDate,
        rejectedBy,
        rejectionReason,
        rejectionDate,
        processInstanceId,
        applicationViolationId,
        reversedById,
        applicationId
        );
    }

    @Override
    public String toString() {
        return "ServiceRequestCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceCode != null ? "serviceCode=" + serviceCode + ", " : "") +
                (phaseType != null ? "phaseType=" + phaseType + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (statusDescription != null ? "statusDescription=" + statusDescription + ", " : "") +
                (statusDate != null ? "statusDate=" + statusDate + ", " : "") +
                (totalFeeAmount != null ? "totalFeeAmount=" + totalFeeAmount + ", " : "") +
                (paidBy != null ? "paidBy=" + paidBy + ", " : "") +
                (paymentMethod != null ? "paymentMethod=" + paymentMethod + ", " : "") +
                (paymentReference != null ? "paymentReference=" + paymentReference + ", " : "") +
                (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
                (rejectedBy != null ? "rejectedBy=" + rejectedBy + ", " : "") +
                (rejectionReason != null ? "rejectionReason=" + rejectionReason + ", " : "") +
                (rejectionDate != null ? "rejectionDate=" + rejectionDate + ", " : "") +
                (processInstanceId != null ? "processInstanceId=" + processInstanceId + ", " : "") +
                (applicationViolationId != null ? "applicationViolationId=" + applicationViolationId + ", " : "") +
                (reversedById != null ? "reversedById=" + reversedById + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
            "}";
    }

}
