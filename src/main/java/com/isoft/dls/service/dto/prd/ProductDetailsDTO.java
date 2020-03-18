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

import com.isoft.dls.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Details DTO
 *
 * @author Mohammad Abulawi
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDetailsDTO extends AbstractAuditingDTO {

    @JsonProperty("hasLearningFile")
    private Boolean hasLearningFile;

    @JsonProperty("hasDubaiLicense")
    private Boolean hasDubaiLicense;

    @JsonProperty("hasHandbook")
    private Boolean hasHandbook;

    public Boolean getHasLearningFile() {
        return hasLearningFile;
    }

    public void setHasLearningFile(Boolean hasLearningFile) {
        this.hasLearningFile = hasLearningFile;
    }

    public Boolean getHasDubaiLicense() {
        return hasDubaiLicense;
    }

    public void setHasDubaiLicense(Boolean hasDubaiLicense) {
        this.hasDubaiLicense = hasDubaiLicense;
    }

    public Boolean getHasHandbook() {
        return hasHandbook;
    }

    public void setHasHandbook(Boolean hasHandbook) {
        this.hasHandbook = hasHandbook;
    }


    @Override
    public String toString() {
        return "ProductDetailsDTO{" +
            "hasLearningFile=" + hasLearningFile +
            ", hasDubaiLicense=" + hasDubaiLicense +
            ", hasHandbook=" + hasHandbook +
            '}';
    }
}
