/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 5:39 AM  - File created.
 */

package com.isoft.dls.service.dto.prd;

import com.isoft.dls.domain.type.MultilingualJsonType;
import com.isoft.dls.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


/**
 * Nationality DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NationalityDTO extends AbstractAuditingDTO implements Serializable {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private MultilingualJsonType name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MultilingualJsonType getName() {
        return name;
    }

    public void setName(MultilingualJsonType name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NationalityDTO{" +
            ", code=" + getCode() +
            ", name=" + getName() +
            "}";
    }
}
