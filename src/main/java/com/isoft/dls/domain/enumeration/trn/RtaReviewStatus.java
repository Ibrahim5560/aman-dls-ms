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
 * Rta Review Status enumeration.
 */
public enum RtaReviewStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    NOT_REQUIRED("NOT_REQUIRED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_RTA_REVIEW_STATUS";

    private String value;

    RtaReviewStatus(final String value) {
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
