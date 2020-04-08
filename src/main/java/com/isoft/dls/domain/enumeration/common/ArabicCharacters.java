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

public enum ArabicCharacters {

    GROUP_1_ALTERNATIVE("ا"),
    GROUP_2_ALTERNATIVE("ى"),
    GROUP_3_ALTERNATIVE("ت"),
    CHAR_1("إ"),
    CHAR_2("أ"),
    CHAR_3("آ"),
    CHAR_4("ي"),
    CHAR_5("ة"),
    CHAR_6("ه"),
    CHAR_7("ث");


    private String value;

    ArabicCharacters(final String value) {
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
