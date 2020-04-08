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
 * The LearningFileStatus enumeration.
 */
public enum LearningFileStatus {
    PENDING_FOR_EYE_TEST("PENDING_FOR_EYE_TEST"),
    PENDING_FOR_MEDICAL_ASSESSMENT("PENDING_FOR_MEDICAL_ASSESSMENT"),
    UNDER_PROCESSING("UNDER_PROCESSING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    DRAFT("DRAFT"),
    EXPIRED("EXPIRED"),
    TRANSFERRED("TRANSFERRED");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. active/inactive)
     */
    public static final String DOMAIN_CODE = "LEFI_STATUS";

    private String value;

    LearningFileStatus(final String value) {
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
