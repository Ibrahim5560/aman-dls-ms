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

package com.isoft.dls.common.util.brm.calculation.fee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Fees List VM
 *
 * @author Tariq Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeesListVM implements Serializable {

    /*
     * Bean Fields.
     */

    @JsonProperty("list")
    private Set<FeeVM> list;

    /*
     * Class Constructors.
     */

    /**
     * Fees Class Default Constructor.
     */
    public FeesListVM() {
        this.list = new HashSet<>();
    }

    /*
     * Bean Fields Accessors.
     */

    /**
     * Get Fees List.
     *
     * @return Fees List.
     */
    public Set<FeeVM> getList() {
        return this.list;
    }

    /**
     * Set Fees List.
     *
     * @param list Fees List.
     */
    public void setList(Set<FeeVM> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "FeesListVM{" +
            "list=" + list +
            '}';
    }
}

