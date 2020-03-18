/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:11 PM  - File created.
 */

package com.isoft.dls.common.errors;

import com.isoft.dls.common.util.CommonUtil;
import org.zalando.problem.Status;

import javax.validation.constraints.NotEmpty;

/**
 * This class used to wrap other system exceptions. Its main purpose is to hide other system exceptions and
 * implementation details.
 *
 * @author Mena Emiel
 * @version 1.00
 */
public class SystemException extends RuntimeException {
    /** Serialization version ID. */
    @SuppressWarnings("compatibility:6476992599168662163")
    private static final long serialVersionUID = -4105396575047568521L;

    /*
     * Instance variables
     */

    private final Status status;

    private final String message;

    /*
     * Constructors
     */

    /**
     * Construct new SystemException object.
     *
     * @param msg Exception message.
     */
    public SystemException(String msg) {
        this(msg, Status.INTERNAL_SERVER_ERROR, null);
    }

    public SystemException(@NotEmpty String msg, @NotEmpty Status status) {
        this(msg, status, null);
    }

    /**
     * Construct new SystemException object.
     *
     * @param msg Exception message.
     * @param parameters Message parameter.
     */
    public SystemException(String msg, Object ...parameters) {
        this(msg, Status.INTERNAL_SERVER_ERROR, parameters);
    }

    /**
     * Nest the geneterated exception inside SystemException object.
     *
     * @param nestedException Generated exception.
     */
    public SystemException(Throwable nestedException) {
        this(nestedException.getMessage(), Status.INTERNAL_SERVER_ERROR, null);
    }

    public SystemException(String msg, Status status, Object... parameters) {
        if (parameters == null || parameters.length == 0) {
            this.message = msg;
        } else {
            this.message = CommonUtil.appendParameters(msg, parameters);
        }
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
