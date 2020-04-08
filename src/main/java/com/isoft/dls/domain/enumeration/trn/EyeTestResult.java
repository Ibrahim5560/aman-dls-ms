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
 * The Eye Test Result enumeration.
 */
public enum EyeTestResult {
    NOT_REQUIRED("NOT_REQUIRED"),
    PENDING("PENDING"),
    FULFILLED("FULFILLED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_EYE_TEST_RESULT";

    private String value;

    EyeTestResult(final String value) {
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
