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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

/**
 * A VM for customer identity application entity.
 */
@ApiModel(description = "application request application (trn_application) entity. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerIdentityApplicationVM implements ViewModel {

    @JsonProperty("activePhase")
    private PhaseType activePhase;

    public PhaseType getActivePhase() {
        return activePhase;
    }

    public void setActivePhase(PhaseType activePhase) {
        this.activePhase = activePhase;
    }


    @Override
    public String toString() {
        return "CustomerIdentityApplicationVM{" +
            "activePhase=" + activePhase +
            '}';
    }
}
