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
 * The AddressType enumeration.
 */
public enum AddressType {
    HOUSE("HOUSE"),
    APARTMENT("APARTMENT"),
    OFFICE("OFFICE");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "DEIN_ADDRESS_TYPE";

    private String value;

    AddressType(final String value) {
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
