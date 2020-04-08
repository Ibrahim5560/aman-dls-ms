package com.isoft.dls.domain.enumeration;

/**
 * The WebServicePropertyName enumeration.
 */
public enum WebServicePropertyName {

    USERNAME("USERNAME"),
    PASSWORD("PASSWORD"),
    BPM_BPD_ID("BPM_BPD_ID"),
    BPM_BRANCH_ID("BPM_BRANCH_ID"),
    BPM_SNAPSHOT_ID("BPM_SNAPSHOT_ID"),
    TIMEOUT("TIMEOUT");

    /** Web Service Property Name Domain Code */
    public static final String DOMAIN_CODE = "WESP_NAME";

    private String value;

    WebServicePropertyName(final String value) {
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
