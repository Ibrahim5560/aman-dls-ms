/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:11 PM  - File created.
 */

package com.isoft.dls.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuleValidationRequest implements Serializable {

    /*
     * Instance Variables
     */

    @JsonProperty("ruleCriteria")
    private RuleCriteria ruleCriteria;

    @JsonProperty("exemptAllRules")
    private Boolean exemptAllRules;

    @JsonProperty("exemptedRules")
    private RuleCodes exemptedRules;

    @JsonProperty("ruleCodes")
    private RuleCodes ruleCodes;

    @JsonProperty("applicationRef")
    private Long applicationRef;

    /*
     * Default Constructor
     */
    public RuleValidationRequest() {
        // Default Constructor
    }

    /*
     * Setters & Getters
     */
    public RuleCriteria getRuleCriteria() {
        return ruleCriteria;
    }

    public void setRuleCriteria(RuleCriteria ruleCriteria) {
        this.ruleCriteria = ruleCriteria;
    }

    public Boolean getExemptAllRules() {
        return exemptAllRules;
    }

    public void setExemptAllRules(Boolean exemptAllRules) {
        this.exemptAllRules = exemptAllRules;
    }

    public RuleCodes getExemptedRules() {
        return exemptedRules;
    }

    public void setExemptedRules(RuleCodes exemptedRules) {
        this.exemptedRules = exemptedRules;
    }

    public Long getApplicationRef() {
        return applicationRef;
    }

    public void setApplicationRef(Long applicationRef) {
        this.applicationRef = applicationRef;
    }

    public RuleCodes getRuleCodes() {
        return ruleCodes;
    }

    public void setRuleCodes(RuleCodes ruleCodes) {
        this.ruleCodes = ruleCodes;
    }

    @Override
    public String toString() {
        return "RuleValidationRequest{" +
            "ruleCriteria=" + ruleCriteria +
            ", exemptAllRules=" + exemptAllRules +
            ", exemptedRules=" + exemptedRules +
            ", ruleCodes=" + ruleCodes +
            ", applicationRef=" + applicationRef +
            '}';
    }
}
