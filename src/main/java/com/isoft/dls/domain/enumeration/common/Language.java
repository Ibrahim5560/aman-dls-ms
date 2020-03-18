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

public enum Language {
    ar("ar"),
    en("en");

    /**
     * Define related domain code to be used for full object retrieval (ex. ar/en)
     */
    public static final String DOMAIN_CODE = "GEN_LANGUAGE";

    private String value;

    Language(final String value) {
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
