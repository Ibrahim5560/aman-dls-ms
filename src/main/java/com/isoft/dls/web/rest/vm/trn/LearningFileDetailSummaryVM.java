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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * A VM of Learning File Detail Summary API.
 */
@ApiModel(description = "Learning File Detail Summary VM. @author Mohammad Qasim.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LearningFileDetailSummaryVM implements ViewModel {

    @JsonProperty("permitExpiryDate")
    private LocalDate permitExpiryDate;

    @JsonProperty("learningFileExpiryDate")
    private LocalDate learningFileExpiryDate;

    public LocalDate getPermitExpiryDate() {
        return permitExpiryDate;
    }

    public void setPermitExpiryDate(LocalDate permitExpiryDate) {
        this.permitExpiryDate = permitExpiryDate;
    }

    public LocalDate getLearningFileExpiryDate() {
        return learningFileExpiryDate;
    }

    public void setLearningFileExpiryDate(LocalDate learningFileExpiryDate) {
        this.learningFileExpiryDate = learningFileExpiryDate;
    }

    @Override
    public String toString() {
        return "LearningFileDetailsVM {" +
            ", permitExpiryDate=" + getPermitExpiryDate() +
            ",learningFileExpiryDate=" + getLearningFileExpiryDate() +
            '}';
    }
}
