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
 * Criteria class for the {@link com.isoft.dls.domain.Application} entity. This class is used
 * in {@link com.isoft.dls.web.rest.ApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter status;

    private InstantFilter statusDate;

    private StringFilter activePhase;

    private StringFilter confirmedBy;

    private InstantFilter confirmationDate;

    private StringFilter rejectedBy;

    private StringFilter rejectionReason;

    private InstantFilter rejectionDate;

    private LongFilter processInstanceId;

    private StringFilter channelCode;

    private LongFilter userId;

    private StringFilter englishCustomerName;

    private StringFilter arabicCustomerName;

    private StringFilter tradeLicenseNo;

    private StringFilter englishCorporateName;

    private StringFilter arabicCorporateName;

    private StringFilter userType;

    private StringFilter userRole;

    private LongFilter applicationTypeId;

    private StringFilter persona;

    private StringFilter personaVersion;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter synchedEntityId;

    public ApplicationCriteria() {
    }

    public ApplicationCriteria(ApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.statusDate = other.statusDate == null ? null : other.statusDate.copy();
        this.activePhase = other.activePhase == null ? null : other.activePhase.copy();
        this.confirmedBy = other.confirmedBy == null ? null : other.confirmedBy.copy();
        this.confirmationDate = other.confirmationDate == null ? null : other.confirmationDate.copy();
        this.rejectedBy = other.rejectedBy == null ? null : other.rejectedBy.copy();
        this.rejectionReason = other.rejectionReason == null ? null : other.rejectionReason.copy();
        this.rejectionDate = other.rejectionDate == null ? null : other.rejectionDate.copy();
        this.processInstanceId = other.processInstanceId == null ? null : other.processInstanceId.copy();
        this.channelCode = other.channelCode == null ? null : other.channelCode.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.englishCustomerName = other.englishCustomerName == null ? null : other.englishCustomerName.copy();
        this.arabicCustomerName = other.arabicCustomerName == null ? null : other.arabicCustomerName.copy();
        this.tradeLicenseNo = other.tradeLicenseNo == null ? null : other.tradeLicenseNo.copy();
        this.englishCorporateName = other.englishCorporateName == null ? null : other.englishCorporateName.copy();
        this.arabicCorporateName = other.arabicCorporateName == null ? null : other.arabicCorporateName.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.userRole = other.userRole == null ? null : other.userRole.copy();
        this.applicationTypeId = other.applicationTypeId == null ? null : other.applicationTypeId.copy();
        this.persona = other.persona == null ? null : other.persona.copy();
        this.personaVersion = other.personaVersion == null ? null : other.personaVersion.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.synchedEntityId = other.synchedEntityId == null ? null : other.synchedEntityId.copy();
    }

    @Override
    public ApplicationCriteria copy() {
        return new ApplicationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public InstantFilter getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(InstantFilter statusDate) {
        this.statusDate = statusDate;
    }

    public StringFilter getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(StringFilter activePhase) {
        this.activePhase = activePhase;
    }

    public StringFilter getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(StringFilter confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public InstantFilter getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(InstantFilter confirmationDate) {
        this.confirmationDate = confirmationDate;
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

    public StringFilter getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(StringFilter channelCode) {
        this.channelCode = channelCode;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getEnglishCustomerName() {
        return englishCustomerName;
    }

    public void setEnglishCustomerName(StringFilter englishCustomerName) {
        this.englishCustomerName = englishCustomerName;
    }

    public StringFilter getArabicCustomerName() {
        return arabicCustomerName;
    }

    public void setArabicCustomerName(StringFilter arabicCustomerName) {
        this.arabicCustomerName = arabicCustomerName;
    }

    public StringFilter getTradeLicenseNo() {
        return tradeLicenseNo;
    }

    public void setTradeLicenseNo(StringFilter tradeLicenseNo) {
        this.tradeLicenseNo = tradeLicenseNo;
    }

    public StringFilter getEnglishCorporateName() {
        return englishCorporateName;
    }

    public void setEnglishCorporateName(StringFilter englishCorporateName) {
        this.englishCorporateName = englishCorporateName;
    }

    public StringFilter getArabicCorporateName() {
        return arabicCorporateName;
    }

    public void setArabicCorporateName(StringFilter arabicCorporateName) {
        this.arabicCorporateName = arabicCorporateName;
    }

    public StringFilter getUserType() {
        return userType;
    }

    public void setUserType(StringFilter userType) {
        this.userType = userType;
    }

    public StringFilter getUserRole() {
        return userRole;
    }

    public void setUserRole(StringFilter userRole) {
        this.userRole = userRole;
    }

    public LongFilter getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(LongFilter applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public StringFilter getPersona() {
        return persona;
    }

    public void setPersona(StringFilter persona) {
        this.persona = persona;
    }

    public StringFilter getPersonaVersion() {
        return personaVersion;
    }

    public void setPersonaVersion(StringFilter personaVersion) {
        this.personaVersion = personaVersion;
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

    public LongFilter getSynchedEntityId() {
        return synchedEntityId;
    }

    public void setSynchedEntityId(LongFilter synchedEntityId) {
        this.synchedEntityId = synchedEntityId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ApplicationCriteria that = (ApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(statusDate, that.statusDate) &&
            Objects.equals(activePhase, that.activePhase) &&
            Objects.equals(confirmedBy, that.confirmedBy) &&
            Objects.equals(confirmationDate, that.confirmationDate) &&
            Objects.equals(rejectedBy, that.rejectedBy) &&
            Objects.equals(rejectionReason, that.rejectionReason) &&
            Objects.equals(rejectionDate, that.rejectionDate) &&
            Objects.equals(processInstanceId, that.processInstanceId) &&
            Objects.equals(channelCode, that.channelCode) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(englishCustomerName, that.englishCustomerName) &&
            Objects.equals(arabicCustomerName, that.arabicCustomerName) &&
            Objects.equals(tradeLicenseNo, that.tradeLicenseNo) &&
            Objects.equals(englishCorporateName, that.englishCorporateName) &&
            Objects.equals(arabicCorporateName, that.arabicCorporateName) &&
            Objects.equals(userType, that.userType) &&
            Objects.equals(userRole, that.userRole) &&
            Objects.equals(applicationTypeId, that.applicationTypeId) &&
            Objects.equals(persona, that.persona) &&
            Objects.equals(personaVersion, that.personaVersion) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(synchedEntityId, that.synchedEntityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        statusDate,
        activePhase,
        confirmedBy,
        confirmationDate,
        rejectedBy,
        rejectionReason,
        rejectionDate,
        processInstanceId,
        channelCode,
        userId,
        englishCustomerName,
        arabicCustomerName,
        tradeLicenseNo,
        englishCorporateName,
        arabicCorporateName,
        userType,
        userRole,
        applicationTypeId,
        persona,
        personaVersion,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        synchedEntityId
        );
    }

    @Override
    public String toString() {
        return "ApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (statusDate != null ? "statusDate=" + statusDate + ", " : "") +
                (activePhase != null ? "activePhase=" + activePhase + ", " : "") +
                (confirmedBy != null ? "confirmedBy=" + confirmedBy + ", " : "") +
                (confirmationDate != null ? "confirmationDate=" + confirmationDate + ", " : "") +
                (rejectedBy != null ? "rejectedBy=" + rejectedBy + ", " : "") +
                (rejectionReason != null ? "rejectionReason=" + rejectionReason + ", " : "") +
                (rejectionDate != null ? "rejectionDate=" + rejectionDate + ", " : "") +
                (processInstanceId != null ? "processInstanceId=" + processInstanceId + ", " : "") +
                (channelCode != null ? "channelCode=" + channelCode + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (englishCustomerName != null ? "englishCustomerName=" + englishCustomerName + ", " : "") +
                (arabicCustomerName != null ? "arabicCustomerName=" + arabicCustomerName + ", " : "") +
                (tradeLicenseNo != null ? "tradeLicenseNo=" + tradeLicenseNo + ", " : "") +
                (englishCorporateName != null ? "englishCorporateName=" + englishCorporateName + ", " : "") +
                (arabicCorporateName != null ? "arabicCorporateName=" + arabicCorporateName + ", " : "") +
                (userType != null ? "userType=" + userType + ", " : "") +
                (userRole != null ? "userRole=" + userRole + ", " : "") +
                (applicationTypeId != null ? "applicationTypeId=" + applicationTypeId + ", " : "") +
                (persona != null ? "persona=" + persona + ", " : "") +
                (personaVersion != null ? "personaVersion=" + personaVersion + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (synchedEntityId != null ? "synchedEntityId=" + synchedEntityId + ", " : "") +
            "}";
    }

}
