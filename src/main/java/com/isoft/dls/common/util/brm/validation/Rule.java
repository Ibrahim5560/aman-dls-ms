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

package com.isoft.dls.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Rule implements Serializable {

    /*
     * Instance Variables
     */
    private String code;
    private String messageAr;
    private String messageEn;
    private Boolean exempted;
    private String level;

    /*
     * Default Constructor
     */
    public Rule() {
        this.code = "";
        this.messageAr = "";
        this.messageEn = "";
        this.exempted = false;
        this.level = "";
    }

    /*
     * Parametrized Constructor
     */
    public Rule(String code, String messageAr, String messageEn, Boolean exempted, String level) {
        this.code = code;
        this.messageAr = messageAr;
        this.messageEn = messageEn;
        this.exempted = exempted;
        this.level = level;
    }

    /*
     * Setters & Getters
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessageAr() {
        return messageAr;
    }

    public void setMessageAr(String messageAr) {
        this.messageAr = messageAr;
    }

    public String getMessageEn() {
        return messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    public Boolean getExempted() {
        return exempted;
    }

    public void setExempted(Boolean exempted) {
        this.exempted = exempted;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Rule{" +
            "code='" + code + '\'' +
            ", messageAr='" + messageAr + '\'' +
            ", messageEn='" + messageEn + '\'' +
            ", exempted=" + exempted +
            ", level='" + level + '\'' +
            '}';
    }
}
