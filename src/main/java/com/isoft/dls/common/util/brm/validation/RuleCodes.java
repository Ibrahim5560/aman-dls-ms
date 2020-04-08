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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RuleCodes implements Serializable {

    /*
     * Instance Variables
     */
    private List<String> list;
    private Boolean reqAllRuleExempted;

    /*
     * Constructor
     */
    public RuleCodes() {
        list = new ArrayList<>();
    }

    /*
     * Setters & Getters
     */
    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RuleCodes{" +
            "list=" + list +
            ", reqAllRuleExempted=" + reqAllRuleExempted +
            '}';
    }
}
