/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.trn;

import com.isoft.dls.domain.enumeration.trn.PhaseType;
import com.isoft.dls.domain.type.ApplicationCriteriaJsonType;
import com.isoft.dls.web.rest.vm.util.ViewModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A VM of Initial Product Offer API.
 */
@ApiModel(description = "productOffer (wrapper of trn_application) entity. @author Yousef Abu Amireh.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InitialProductOfferVM implements ViewModel {

    @NotNull
    @JsonProperty("applicationReferenceNo")
    private Long applicationId;

    @NotNull
    @JsonProperty("applicationCriteria")
    private ApplicationCriteriaJsonType applicationCriteria;

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    @JsonProperty("persona")
    private String persona;

    @JsonProperty("personaVersion")
    private String personaVersion;

    @JsonProperty("finalOffer")
    private Boolean finalOffer;

    @JsonProperty("email")
    private String email;

    @JsonProperty("customerNo")
    private Long customerNo;

    @JsonProperty("corporateMandatory")
    private boolean corporateMandatory;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationCriteriaJsonType getApplicationCriteria() {
        return applicationCriteria;
    }

    public void setApplicationCriteria(ApplicationCriteriaJsonType applicationCriteria) {
        this.applicationCriteria = applicationCriteria;
    }

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getPersonaVersion() {
        return personaVersion;
    }

    public void setPersonaVersion(String personaVersion) {
        this.personaVersion = personaVersion;
    }

    public Boolean getFinalOffer() {
        return finalOffer;
    }

    public void setFinalOffer(Boolean finalOffer) {
        this.finalOffer = finalOffer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Long customerNo) {
        this.customerNo = customerNo;
    }

    public boolean isCorporateMandatory() {
        return corporateMandatory;
    }

    public void setCorporateMandatory(boolean corporateMandatory) {
        this.corporateMandatory = corporateMandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InitialProductOfferVM initialProductOfferVM = (InitialProductOfferVM) o;
        if (initialProductOfferVM.getApplicationId() == null || getApplicationId() == null) {
            return false;
        }
        return Objects.equals(getApplicationId(), initialProductOfferVM.getApplicationId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getApplicationId());
    }

    @Override
    public String toString() {
        return "InitialProductOfferVM{" +
            "applicationId=" + applicationId +
            ", applicationCriteria=" + applicationCriteria +
            ", activePhase=" + activePhase +
            ", persona='" + persona + '\'' +
            ", personaVersion='" + personaVersion + '\'' +
            ", finalOffer=" + finalOffer +
            ", email='" + email + '\'' +
            ", customerNo=" + customerNo +
            ", corporateMandatory=" + corporateMandatory +
            '}';
    }
}
