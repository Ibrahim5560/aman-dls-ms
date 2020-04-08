/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:31 AM  - File created.
 */

package com.isoft.dls.domain.enumeration.common;

/**
 * The OtpInformationStatus enumeration.
 */
public enum OtpInformationStatus {
    WAITING_VERIFICATION_MESSAGE("WAITING_VERIFICATION_MESSAGE"),
    READY_FOR_VERIFICATION("READY_FOR_VERIFICATION"),
    VERIFIED("VERIFIED"),
    RETIRED("RETIRED"),
    EXPIRED("EXPIRED"),
    FAILED_TO_SEND("FAILED_TO_SEND"),
    FAILED_TO_VERIFY("FAILED_TO_VERIFY");

    /**
     * Define related domain code to be used for full object retrieval (ex. new/used)
     */
    public static final String DOMAIN_CODE = "OTIN_STATUS";

    private String value;

    OtpInformationStatus(final String value) {
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
