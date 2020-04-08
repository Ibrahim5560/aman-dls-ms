/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 3:34 AM  - File created.
 */

package com.isoft.dls.domain.enumeration.trn;

/**
 * The Noc Type enumeration.
 */
public enum NocType {
    REQUIRED("REQUIRED"),
    NOT_REQUIRED("NOT_REQUIRED"),
    FULFILLED_ELECTRONIC("FULFILLED_ELECTRONIC"),
    FULFILLED_MANUAL("FULFILLED_MANUAL"),
    FULFILLED_MANUAL_REVIEWED("FULFILLED_MANUAL_REVIEWED");


    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_NOC_TYPE";

    private String value;

    NocType(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }
}
