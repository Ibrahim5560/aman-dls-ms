/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:12 PM  - File created.
 */

package com.isoft.dls.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Rules implements Serializable {

    /*
     * Instance Variables
     */
    private List<Rule> list;

    /*
     * Constructor
     */
    public Rules() {
        list = new ArrayList<>();
    }

    /*
     * Setters & Getters
     */
    public List<Rule> getList() {
        return list;
    }

    public void setList(List<Rule> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Rules{" +
            "list=" + list +
            '}';
    }
}
