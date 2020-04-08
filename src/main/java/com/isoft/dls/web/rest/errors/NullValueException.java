/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 4:55 PM  - File created.
 */

package com.isoft.dls.web.rest.errors;

import com.isoft.dls.common.errors.SystemException;

public class NullValueException extends SystemException {

    private static final long serialVersionUID = 1L;

    public NullValueException(String fieldName) {
        super("Null " + fieldName + " value!");
    }
}
