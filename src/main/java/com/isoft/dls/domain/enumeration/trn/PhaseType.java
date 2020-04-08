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
 * The PhaseType enumeration.
 */
public enum PhaseType {
    CUSTOMER_ELIGIBILITY("CUSTOMER_ELIGIBILITY"),
    DRIVING_LEARNING_FILE_PROCESSING("DRIVING_LEARNING_FILE_PROCESSING"),
    DRIVING_LEARNING_FILE_RTA_AUDIT("DRIVING_LEARNING_FILE_RTA_AUDIT"),
    DRIVING_LEARNING_FILE_DS_AUDIT("DRIVING_LEARNING_FILE_DS_AUDIT"),
    WAITING_FOR_PAYMENT_CLEARANCE("WAITING_FOR_PAYMENT_CLEARANCE"),
    READY_FOR_CONTRACT_SIGN("READY_FOR_CONTRACT_SIGN"),
    EYE_TEST("EYE_TEST"),
    THEORY_LECTURE("THEORY_LECTURE"),
    THEORY_TEST("THEORY_TEST"),
    DRIVING_LESSONS("DRIVING_LESSONS"),
    YARD_TEST("YARD_TEST"),
    ADVANCED_PRACTICAL_TRAINING("ADVANCED_PRACTICAL_TRAINING"),
    ROAD_TEST("ROAD_TEST"),
    PRINT_LICENSE("PRINT_LICENSE"),
    APPLICATION_REJECTION("APPLICATION_REJECTION"),
    EXEMPTION_AUDITING("EXEMPTION_AUDITING"),
    MISSING_DOCUMENT("MISSING_DOCUMENT"),
    // create user delivery
    CREATE_USER_DELIVERY("CREATE_USER_DELIVERY"),
    GET_REQUEST_DETAILS("GET_REQUEST_DETAILS"),
    HUMAN_TASK_APPROVAL("HUMAN_TASK_APPROVAL");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "SERE_PHASE_TYPE";

    private String value;

    PhaseType(final String value) {
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
