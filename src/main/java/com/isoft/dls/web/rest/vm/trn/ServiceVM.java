/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.trn;

import com.isoft.dls.domain.type.MultilingualJsonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Type;

/**
 * A VM of service
 */
@ApiModel(description = "service VM . @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServiceVM {

    @JsonProperty("code")
    private Long code;

    @Type(type = "json")
    //@Convert(converter = MultilingualJsonConverter.class)
    @JsonProperty("name")
    private MultilingualJsonType name;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
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
        return "ServiceVM{" +
            "code=" + code +
            ", name=" + name +
            '}';
    }
}
