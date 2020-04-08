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

/**
 * The ServiceRequestStatus enumeration.
 */
public enum ServiceRequestStatus {
    UNDER_PROCESSING("UNDER_PROCESSING"),
    VERIFIED_AND_LOCKED("VERIFIED_AND_LOCKED"),
    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "SERE_STATUS";

    private String value;

    ServiceRequestStatus(final String value) {
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
