/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:10 PM  - File created.
 */

package com.isoft.dls.domain.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.isoft.dls.common.errors.SystemException;
import com.isoft.dls.common.util.CommonUtil;
import com.isoft.dls.common.util.StringUtil;
import com.isoft.dls.domain.type.MultilingualJsonType;

import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * Multilingual Json Converter
 *
 * This class is designed mainly to be used for all annotated jpa attributes
 * to convert its value into db json column and read from the same db
 * column then convert it back into MultilingualJsonType.
 *
 * @author Mena Emiel.
 */
public class MultilingualJsonConverter implements AttributeConverter<MultilingualJsonType, String> {

    @Override
    public String convertToDatabaseColumn(MultilingualJsonType jsonAttributeValue) {
        try {
            if (jsonAttributeValue == null) {
                return null;
            }
            return CommonUtil.getMapper().writeValueAsString(jsonAttributeValue);
        } catch (JsonProcessingException e) {
            throw new SystemException("Error while writing JSON content :: ", e);
        }
    }

    @Override
    public MultilingualJsonType convertToEntityAttribute(String jsonColumnValue) {
        try {
            if (StringUtil.isBlank(jsonColumnValue)) {
                return null;
            }
            return CommonUtil.getMapper().readValue(jsonColumnValue, MultilingualJsonType.class);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
