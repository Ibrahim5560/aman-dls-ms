package com.isoft.dls.domain.enumeration;

/**
 * The WebServiceName enumeration.
 */
public enum WebServiceName {

    INITIATE_PROCESS("INITIATE_PROCESS"),
    USER_INBOX("USER_INBOX"),
    ACCEPT_CONTRACT("ACCEPT_CONTRACT"),
    FINISH_TASK("FINISH_TASK"),
    CLAIM_TASK("CLAIM_TASK"),
    PROCESS_DETAILS("PROCESS_DETAILS"),
    ACTIVE_TASK("ACTIVE_TASK"),
    TASK_DETAILS("TASK_DETAILS"),
    PERSONA_CATEGORY("PERSONA_CATEGORY"),
    RULE_VALIDATION("RULE_VALIDATION"),
    CUSTOMER_DEMOGRAPHIC("CUSTOMER_DEMOGRAPHIC"),
    CREATE_TRAFFIC_FILE("CREATE_TRAFFIC_FILE"),
    PAY_EWALLET("PAY_EWALLET"),
    UTS_CLIENT("UTS_CLIENT"),
    CTS_CLIENT("CTS_CLIENT"),
    SPECIAL_COUNTRY("SPECIAL_COUNTRY"),
    CREATE_USER_DELIVERY("CREATE_USER_DELIVERY");

    /** Web Service Name Domain Code */
    public static final String DOMAIN_CODE = "WESE_NAME";

    private String value;

    WebServiceName(final String value) {
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
